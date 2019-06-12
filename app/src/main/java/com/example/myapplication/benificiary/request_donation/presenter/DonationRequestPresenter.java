package com.example.myapplication.benificiary.request_donation.presenter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.*;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import com.example.myapplication.R;
import com.example.myapplication.benificiary.request_donation.EventImagesAdapter;
import com.example.myapplication.benificiary.request_donation.ImageVideoData;
import com.example.myapplication.benificiary.request_donation.model.DonationRequestModel;
import com.example.myapplication.benificiary.request_donation.model.IDonationRequestModel;
import com.example.myapplication.benificiary.request_donation.view.DonationRequstFragment;
import com.example.myapplication.benificiary.request_donation.view.IDonationRequestView;
import com.example.myapplication.helperClass.CustomFileUtils;
import com.example.myapplication.helperClass.CustomPermissions;
import com.example.myapplication.helperClass.CustomUtils;
import com.example.myapplication.helperClass.PrefManager;
import com.iceteck.silicompressorr.SiliCompressor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.helperClass.CustomUtils.IMAGE_LIMIT;

public class DonationRequestPresenter implements IDonationRequestPresenter, IDonationRequestModel.donationRequestListener {
    Context context;
    IDonationRequestView iDonationRequestView;
    IDonationRequestModel iDonationRequestModel;
    Activity activity;
    public static ArrayList<ImageVideoData> image_thumbnails;
    public static ArrayList<File> imageFiles;

    public DonationRequestPresenter(Context context, IDonationRequestView iDonationRequestView, Activity activity) {
        this.context = context;
        this.activity = activity;
        this.iDonationRequestView = iDonationRequestView;
        iDonationRequestModel = new DonationRequestModel();
        imageFiles = new ArrayList<>();
        image_thumbnails = new ArrayList<ImageVideoData>();
        iDonationRequestView.setImageAdapter();
    }

    @Override
    public void requestDonation(String amount, String desc, List<File> imagesFile) {
        iDonationRequestView.setProgressVisibility(View.VISIBLE);
        iDonationRequestModel.requestForDonation(amount, desc, imagesFile, PrefManager.getInstance(context).getUserId(), this);

    }

    @Override
    public void validateData(String amount, String desc, List<File> imagesFile) {
        if (amount.isEmpty())
        {
            iDonationRequestView.getValidationResult(1);
            return;
        }
        else
            if (desc.isEmpty())
            {
                iDonationRequestView.getValidationResult(2);
                return;
            }
            else
                 if (imagesFile.size()==0)
                 {
                     iDonationRequestView.getValidationResult(3);
                     return;
                 }
                 else
                     iDonationRequestView.getValidationResult(4);
    }

    @Override
    public void selectImage() {
        if (imageFiles.size() >= IMAGE_LIMIT) {

            CustomUtils.showAlertDialog(context, context.getString(R.string.can_not_share_more_than_five_images));
        } else {

            final String[] items = new String[]{context.getString(R.string.camera), context.getString(R.string.gallery)};

            AlertDialog.Builder ad = new AlertDialog.Builder(context);
            ad.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    switch (i) {
                        case 0:

                            boolean result_camera = CustomPermissions.checkCameraPermission(activity);
                            if (result_camera) {
                                cameraImageIntent();
                            }
                            break;

                        case 1:
                            boolean result_gallery = CustomPermissions.checkPermissionForFileAccess(activity);
                            if (result_gallery) {
                                galleryImageIntent();
                            }
                            break;
                    }
                }
            });
            ad.show();
        }
    }

    @Override
    public void cameraImageIntent() {
        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            PackageManager pm = context.getPackageManager();

            final ResolveInfo mInfo = pm.resolveActivity(i, 0);

            Intent intent = new Intent();
            intent.setComponent(new ComponentName(mInfo.activityInfo.packageName, mInfo.activityInfo.name));
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            activity.startActivityForResult(i, CustomPermissions.MY_PERMISSIONS_REQUEST_CAMERA);
        } catch (Exception e) {
            Log.i("launch_camera", "Unable to launch camera: " + e);
        }

    }

    @Override
    public void galleryImageIntent() {
        try {
            //Open the specific App Info page:
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setData(Uri.parse("package:" + "com.android.providers.downloads"));
            intent.setType("image/*");
            activity.startActivityForResult(Intent.createChooser(intent, activity.getString(R.string.select)), CustomPermissions.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

        } catch (ActivityNotFoundException e) {
            e.printStackTrace();

            //Open the generic Apps page:
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setType("image/*");
            activity.startActivityForResult(Intent.createChooser(intent, activity.getString(R.string.select)), CustomPermissions.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void getOnActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case CustomPermissions.MY_PERMISSIONS_REQUEST_CAMERA:
                Bitmap bitmap_thumbnail = (Bitmap) data.getExtras().get("data");
                bitmap_thumbnail.getByteCount();
                Uri tempUri = CustomUtils.getImageUri(context.getApplicationContext(), bitmap_thumbnail);
                File finalFile = new File(CustomUtils.getRealPathFromURI(context.getApplicationContext(), tempUri));
                CustomUtils.showLog("Camera File path ", finalFile.getAbsolutePath() + "");
                if (imageFiles.size() <= CustomUtils.IMAGE_LIMIT) {
                    ArrayList<String> selected_image = new ArrayList<>();
                    selected_image.add(tempUri.toString());
                    Bitmap picture = (Bitmap) data.getExtras().get("data");
                    new ImageCompressAsyncTask(context,picture).execute(selected_image);
                } else {
                    CustomUtils.showAlertDialog(context, context.getString(R.string.can_not_share_more_than_five_images));
                }
                break;

            case CustomPermissions.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    int imageCount = imageFiles.size();
                    int selectedImages = imageCount + count;
                    if (selectedImages > IMAGE_LIMIT) {
                        CustomUtils.showAlertDialog(context, context.getString(R.string.can_not_share_more_than_five_images));
                        return;
                    } else {
                        ArrayList<String> images = new ArrayList<>();

                        for (int i = 0; i < count; i++) {
                            images.add(data.getClipData().getItemAt(i).getUri().toString());
                            //  CustomUtil.showLog("Image selected ", data.getClipData().getItemAt(i).getUri().toString());

                            String picturePath = CustomFileUtils.getPath(context.getApplicationContext(), data.getClipData().getItemAt(i).getUri());
                            File file = new File(picturePath);
                            imageFiles.add(file);
                            long lengthInMB = lengthInMB = (file.length() / 1024) / 1024;
                            ArrayList<String> image = new ArrayList<>();
                            image.add(data.getClipData().getItemAt(i).getUri().toString());

                            if (lengthInMB >= 20) {
                                new ImageCompressAsyncTask(context,null).execute(image);
                            } else {

                                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                                ImageVideoData image_v = new ImageVideoData();
                                  /* image_v.setBitmap(bitmap);
                                    image_v.setPath(picturePath);
                                    image_thumbnails.add(image_v);
*/

                                ExifInterface ei = null;
                                try {
                                    ei = new ExifInterface(picturePath);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                        ExifInterface.ORIENTATION_UNDEFINED);

                                Bitmap rotatedBitmap = null;
                                switch (orientation) {

                                    case ExifInterface.ORIENTATION_ROTATE_90:
                                        rotatedBitmap = rotateImage(bitmap, 90);
                                        break;

                                    case ExifInterface.ORIENTATION_ROTATE_180:
                                        rotatedBitmap = rotateImage(bitmap, 180);
                                        break;

                                    case ExifInterface.ORIENTATION_ROTATE_270:
                                        rotatedBitmap = rotateImage(bitmap, 270);
                                        break;

                                    case ExifInterface.ORIENTATION_NORMAL:
                                    default:
                                        rotatedBitmap = bitmap;
                                }
                                //File file1 = new File(String.valueOf(data.getData()));

                                image_v.setBitmap(rotatedBitmap);
                                image_v.setPath(picturePath);
                                image_thumbnails.add(image_v);
                                iDonationRequestView.setImageAdapter();

                                       /* adapter = new EventImagesAdapter(getApplicationContext(), CreateEventActivity.this, image_thumbnails);
                                        rv_event_image.setLayoutManager(llm_images);
                                        rv_event_image.setAdapter(adapter);*/
                            }


                        }

                        //    new ImageCompressAsyncTask(CreateEventActivity.this).execute(images);
                        if (imageFiles.size() <= IMAGE_LIMIT) {
                            iDonationRequestView.setImageAdapter();

                        } else {
                            //showToast(getString(R.string.can_not_share_more_than_five_images), CreateEventActivity.this);
                            if (imageCount <= imageFiles.size()) {
                                for (int i = imageFiles.size(); i > imageCount; i--) {
                                    image_thumbnails.remove(i - 1);
                                    imageFiles.remove(i - 1);
                                }

                            }
                            CustomUtils.showAlertDialog(context, context.getString(R.string.can_not_share_more_than_five_images));
                        }
                    }
                } else if (data.getData() != null) {
                    Uri selectedImage = data.getData();
                    String picturePath = CustomFileUtils.getPath(context.getApplicationContext(), selectedImage);
                    File file = new File(picturePath);
                    imageFiles.add(file);
                    if (imageFiles.size() <= IMAGE_LIMIT) {
                        long lengthInMB = (file.length() / 1024) / 1024;
                        ArrayList<String> image = new ArrayList<>();
                        image.add(selectedImage.toString());
                        if (lengthInMB >= 20) {
                            new ImageCompressAsyncTask(context,null).execute(image);
                        } else {

                            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                            ImageVideoData image_v = new ImageVideoData();

                            ExifInterface ei = null;
                            try {
                                ei = new ExifInterface(picturePath);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                    ExifInterface.ORIENTATION_UNDEFINED);

                            Bitmap rotatedBitmap = null;
                            switch (orientation) {

                                case ExifInterface.ORIENTATION_ROTATE_90:
                                    rotatedBitmap = rotateImage(bitmap, 90);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_180:
                                    rotatedBitmap = rotateImage(bitmap, 180);
                                    break;

                                case ExifInterface.ORIENTATION_ROTATE_270:
                                    rotatedBitmap = rotateImage(bitmap, 270);
                                    break;

                                case ExifInterface.ORIENTATION_NORMAL:
                                default:
                                    rotatedBitmap = bitmap;
                            }
                            //File file1 = new File(String.valueOf(data.getData()));

                            image_v.setBitmap(rotatedBitmap);
                            image_v.setPath(picturePath);
                            image_thumbnails.add(image_v);
                            iDonationRequestView.setImageAdapter();
                        }
                    } else {
                        CustomUtils.showAlertDialog(context, context.getString(R.string.can_not_share_more_than_five_images));
                    }
                }
                break;

        }


    }

    public class ImageCompressAsyncTask extends AsyncTask<List<String>, String, String> {
        Context context;
        Uri selectedImage;
        Bitmap picture;
        public ImageCompressAsyncTask(Context context,Bitmap picture) {
            this.context = context;
            this.picture = picture;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            iDonationRequestView.setProgressVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(List<String>... paths) {
            //final ArrayList<String> filePaths=
            //getImages(paths[0]);
            //iDonationRequestView.setProgressVisibility(View.GONE);
            for (String filePaths : paths[0]) {
                //   final int finalI = i;
                String filePath = null;
                try {
                    filePath = SiliCompressor.with(context).compress(filePaths, new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + context.getPackageName() + "/media/images"));
                    File imageFile = new File(filePath);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] bt = bos.toByteArray();
                    String encodedImageString1 = Base64.encodeToString(bt, Base64.DEFAULT);
                    byte[] decodedString1 = Base64.decode(encodedImageString1.getBytes(), Base64.NO_WRAP);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);
                    ImageVideoData image_v = new ImageVideoData();
                    if (picture==null)
                    image_v.setBitmap(decodedByte);
                    else
                        image_v.setBitmap(picture);

                    image_v.setPath(filePath);
                    image_thumbnails.add(image_v);
                    imageFiles.add(imageFile);
                    Thread.sleep(500);
                    //  iDonationRequestView.setProgressVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "";
        }


        @Override
        protected void onPostExecute(String compressedFilePath) {
            super.onPostExecute(compressedFilePath);
            iDonationRequestView.notifyDataSetChanged();
            iDonationRequestView.setProgressVisibility(View.GONE);
            Log.i("Silicompressor", "Path: " + compressedFilePath);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix,
                true);
    }

    @Override
    public void onDonationRequestFinished(String status) {
        iDonationRequestView.setProgressVisibility(View.GONE);
        iDonationRequestView.getDonationRequestResult(status);
    }

    @Override
    public void onDonationRequestFailure(Throwable t) {
        iDonationRequestView.setProgressVisibility(View.GONE);
        iDonationRequestView.getDonationRequestFailureResult(t);
    }
}
