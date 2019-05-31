package com.example.myapplication.benificiary.request_donation.view;

import android.annotation.TargetApi;
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
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.myapplication.R;
import com.example.myapplication.benificiary.request_donation.EventImagesAdapter;
import com.example.myapplication.benificiary.request_donation.ImageVideoData;
import com.example.myapplication.benificiary.request_donation.presenter.DonationRequestPresenter;
import com.example.myapplication.benificiary.request_donation.presenter.IDonationRequestPresenter;
import com.example.myapplication.helperClass.CustomFileUtils;
import com.example.myapplication.helperClass.CustomPermissions;
import com.example.myapplication.helperClass.CustomUtils;
import com.iceteck.silicompressorr.SiliCompressor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.myapplication.helperClass.CustomUtils.IMAGE_LIMIT;

public class DonationRequstFragment extends Fragment implements View.OnClickListener , IDonationRequestView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ArrayList<File> imageFiles;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
View v;
@BindView(R.id.et_amount)
    EditText et_amount;
    @BindView(R.id.et_desc)
    EditText et_desc;
    @BindView(R.id.btn_upload)
    Button btn_upload;
    @BindView(R.id.btn_submit)
    Button btn_submit;
    @BindView(R.id.rv_images)
    RecyclerView rv_images;
    LinearLayoutManager llm_images;
    ArrayList<ImageVideoData> image_thumbnails;
IDonationRequestPresenter iDonationRequestPresenter;
    EventImagesAdapter adapter;
    private OnFragmentInteractionListener mListener;

    public DonationRequstFragment() {
        // Required empty public constructor
    }


    public static DonationRequstFragment newInstance(String param1, String param2) {
        DonationRequstFragment fragment = new DonationRequstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_donation_requst, container, false);
        ButterKnife.bind(this,v);
        imageFiles = new ArrayList<>();
        image_thumbnails = new ArrayList<ImageVideoData>();
        llm_images = new LinearLayoutManager(getActivity());
        llm_images.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapter = new EventImagesAdapter(getActivity(), getActivity(), image_thumbnails, "create");
        iDonationRequestPresenter=new DonationRequestPresenter(getActivity(),this);
        btn_submit.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_submit:
                iDonationRequestPresenter.requestDonation(et_amount.getText().toString(),et_desc.getText().toString(),imageFiles);
                break;
            case R.id.btn_upload:
                CustomUtils.hideKeyboard(v, getActivity().getApplicationContext());
                if (imageFiles.size() >= IMAGE_LIMIT) {

                    CustomUtils.showAlertDialog(getActivity(), getString(R.string.can_not_share_more_than_five_images));
                } else {
                    selectImage();
                }
                break;
        }

    }
    private void selectImage() {
        final String[] items = new String[]{getString(R.string.camera), getString(R.string.gallery)};

        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        ad.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (i) {
                    case 0:
                        boolean result_camera = CustomPermissions.checkCameraPermission(getActivity());
                        if (result_camera) {
                            cameraImageIntent();
                        }
                        break;

                    case 1:
                        boolean result_gallery = CustomPermissions.checkPermissionForFileAccess(getActivity());
                        if (result_gallery) {
                            galleryImageIntent();
                        }
                        break;
                }
            }
        });
        ad.show();
    }

    private void galleryImageIntent() {


        try {
            //Open the specific App Info page:
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setData(Uri.parse("package:" + "com.android.providers.downloads"));
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, getString(R.string.select)), CustomPermissions.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

        } catch (ActivityNotFoundException e) {
            e.printStackTrace();

            //Open the generic Apps page:
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, getString(R.string.select)), CustomPermissions.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }

      /*  Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select)), CustomPermissions.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);*/
    }

    private void cameraImageIntent() {

        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            PackageManager pm = getActivity().getPackageManager();

            final ResolveInfo mInfo = pm.resolveActivity(i, 0);

            Intent intent = new Intent();
            intent.setComponent(new ComponentName(mInfo.activityInfo.packageName, mInfo.activityInfo.name));
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivityForResult(i, CustomPermissions.MY_PERMISSIONS_REQUEST_CAMERA);
        } catch (Exception e) {
            Log.i("launch_camera", "Unable to launch camera: " + e);
        }


    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CustomPermissions.MY_PERMISSIONS_REQUEST_CAMERA:
                    Bitmap bitmap_thumbnail = (Bitmap) data.getExtras().get("data");
                    bitmap_thumbnail.getByteCount();
                    Uri tempUri = CustomUtils.getImageUri(getActivity().getApplicationContext(), bitmap_thumbnail);
                    File finalFile = new File(CustomUtils.getRealPathFromURI(getActivity().getApplicationContext(), tempUri));
                    CustomUtils.showLog("Camera File path ", finalFile.getAbsolutePath() + "");
                    if (imageFiles.size() <= CustomUtils.IMAGE_LIMIT) {
                        ArrayList<String> selected_image = new ArrayList<>();
                        selected_image.add(tempUri.toString());
                        new ImageCompressAsyncTask(getActivity()).execute(selected_image);
                    } else {
                        CustomUtils.showAlertDialog(getActivity(), getString(R.string.can_not_share_more_than_five_images));
                    }
                    break;

                case CustomPermissions.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                    if (data.getClipData() != null) {
                        int count = data.getClipData().getItemCount();
                        int imageCount = imageFiles.size();
                        int selectedImages = imageCount + count;
                        if (selectedImages > IMAGE_LIMIT) {
                            CustomUtils.showAlertDialog(getActivity(), getString(R.string.can_not_share_more_than_five_images));
                            return;
                        } else {
                            ArrayList<String> images = new ArrayList<>();

                            for (int i = 0; i < count; i++) {
                                images.add(data.getClipData().getItemAt(i).getUri().toString());
                                //  CustomUtil.showLog("Image selected ", data.getClipData().getItemAt(i).getUri().toString());

                                String picturePath = CustomFileUtils.getPath(getActivity().getApplicationContext(), data.getClipData().getItemAt(i).getUri());
                                File file = new File(picturePath);
                                imageFiles.add(file);
                                long lengthInMB = lengthInMB = (file.length() / 1024) / 1024;
                                ArrayList<String> image = new ArrayList<>();
                                image.add(data.getClipData().getItemAt(i).getUri().toString());

                                if (lengthInMB >= 20) {
                                    new ImageCompressAsyncTask(getActivity()).execute(image);
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

                                       /* adapter = new EventImagesAdapter(getApplicationContext(), CreateEventActivity.this, image_thumbnails);
                                        rv_event_image.setLayoutManager(llm_images);
                                        rv_event_image.setAdapter(adapter);*/
                                }


                            }

                            //    new ImageCompressAsyncTask(CreateEventActivity.this).execute(images);
                            if (imageFiles.size() <= IMAGE_LIMIT) {
                                adapter = new EventImagesAdapter(getActivity(), getActivity(), image_thumbnails, "create");
                                rv_images.setLayoutManager(llm_images);
                                rv_images.setAdapter(adapter);
                            } else {
                                //showToast(getString(R.string.can_not_share_more_than_five_images), CreateEventActivity.this);
                                if (imageCount <= imageFiles.size()) {
                                    for (int i = imageFiles.size(); i > imageCount; i--) {
                                        image_thumbnails.remove(i - 1);
                                        imageFiles.remove(i - 1);
                                    }

                                }
                                CustomUtils.showAlertDialog(getActivity(), getString(R.string.can_not_share_more_than_five_images));
                            }
                        }
                    } else if (data.getData() != null) {
                        Uri selectedImage = data.getData();
                        String picturePath = CustomFileUtils.getPath(getActivity().getApplicationContext(), selectedImage);
                        File file = new File(picturePath);
                        imageFiles.add(file);
                        if (imageFiles.size() <= IMAGE_LIMIT) {
                            long lengthInMB = (file.length() / 1024) / 1024;
                            ArrayList<String> image = new ArrayList<>();
                            image.add(selectedImage.toString());
                            if (lengthInMB >= 20) {
                                new ImageCompressAsyncTask(getActivity()).execute(image);
                            } else {

                                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                                ImageVideoData image_v = new ImageVideoData();
                             /* image_v.setBitmap(bitmap);
                                image_v.setPath(picturePath);
                                image_thumbnails.add(image_v);*/

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
                                adapter = new EventImagesAdapter(getActivity(), getActivity(), image_thumbnails, "create");
                                rv_images.setLayoutManager(llm_images);
                                rv_images.setAdapter(adapter);
                            }
                        } else {
                            CustomUtils.showAlertDialog(getActivity(), getString(R.string.can_not_share_more_than_five_images));
                        }
                    }
                    break;




            }
        }



    }

    ProgressDialog progressDialog;

    @Override
    public void setProgressVisibility(int visibility) {

    }

    @Override
    public void getDonationRequestResult(String status) {
        Toast.makeText(getActivity(), ""+status, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void getDonationRequestFailureResult(Throwable t) {
        Toast.makeText(getActivity(), ""+t, Toast.LENGTH_SHORT).show();

    }

    public class ImageCompressAsyncTask extends AsyncTask<List<String>, String, String> {
        Context mContext;
        Uri selectedImage;

        public ImageCompressAsyncTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle(getString(R.string.app_name));

            progressDialog.setMessage(getString(R.string.please_wait));
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(List<String>... paths) {
            //final ArrayList<String> filePaths=
            //getImages(paths[0]);
            progressDialog.dismiss();
            for (String filePaths : paths[0]) {
                //   final int finalI = i;
                String filePath = null;
                try {
                    filePath = SiliCompressor.with(getActivity()).compress(filePaths, new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getActivity().getPackageName() + "/media/images"));
                    File imageFile = new File(filePath);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] bt = bos.toByteArray();
                    String encodedImageString1 = Base64.encodeToString(bt, Base64.DEFAULT);
                    byte[] decodedString1 = Base64.decode(encodedImageString1.getBytes(), Base64.NO_WRAP);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString1, 0, decodedString1.length);
                    ImageVideoData image_v = new ImageVideoData();
                    image_v.setBitmap(decodedByte);
                    image_v.setPath(filePath);
                    image_thumbnails.add(image_v);
                    imageFiles.add(imageFile);
                    Thread.sleep(500);
                    progressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return "";
        }


        @Override
        protected void onPostExecute(String compressedFilePath) {
            super.onPostExecute(compressedFilePath);
            adapter.notifyDataSetChanged();
            progressDialog.dismiss();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog.dismiss();
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
