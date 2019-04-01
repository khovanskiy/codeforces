package com.example.PhotoTaker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class PhotoTakerActivity extends Activity {
    private static final int NO_CAMERA = -1;
    private static final int SELECT_PICTURE = 1;

    private ImageButton galleryButton, takePhotoButton;
    private Switch cameraSwitch;
    private Camera camera = null;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private int currentCameraId;
    private Context context;

    private void stopCamera() {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    private void startCamera(int cameraId) {
        try {
            camera = Camera.open(cameraId);
            camera.setPreviewDisplay(surfaceHolder);
            Camera.Size size = camera.getParameters().getPreviewSize();
            float aspectRatio = (float) size.width / size.height;
            int newWidth, newHeight;
            if (surfaceView.getWidth() / surfaceView.getHeight() < aspectRatio) {
                newWidth = Math.round(surfaceView.getHeight() * aspectRatio);
                newHeight = surfaceView.getHeight();
            } else {
                newWidth = surfaceView.getWidth();
                newHeight = Math.round(surfaceView.getWidth() / aspectRatio);
            }
            ViewGroup.LayoutParams layoutParams = surfaceView.getLayoutParams();
            layoutParams.width = newWidth;
            layoutParams.height = newHeight;
            surfaceView.setLayoutParams(layoutParams);
            camera.startPreview();
            currentCameraId = cameraId;
        } catch (Exception e) {
            Toast toast = Toast.makeText(context, "Failed to open camera", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void setCamera(int cameraFacing) {
        stopCamera();
        int cameraNumber = Camera.getNumberOfCameras();
        currentCameraId = NO_CAMERA;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int cameraId = 0; cameraId < cameraNumber; cameraId++) {
            Camera.getCameraInfo(cameraId, cameraInfo);
            if (cameraInfo.facing == cameraFacing) {
                startCamera(cameraId);
                if (currentCameraId == cameraId) return;
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phototakeractivity);
        context = this;
        galleryButton = (ImageButton) findViewById(R.id.phototakeractivity_gallerybutton);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTURE);
            }
        });
        takePhotoButton = (ImageButton) findViewById(R.id.phototakeractivity_takephotobutton);
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camera != null) {
                    camera.takePicture(null, null, new Camera.PictureCallback() {
                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            Toast toast = Toast.makeText(context, "The photo has been taken", Toast.LENGTH_LONG);
                            toast.show();
                            PhotoFilteringActivity.image = BitmapFactory.decodeByteArray(data, 0, data.length);
                            Intent intent = new Intent(context, PhotoFilteringActivity.class);
                            context.startActivity(intent);
                        }
                    });
                }
            }
        });
        cameraSwitch = (Switch) findViewById(R.id.phototakeractivity_cameraswitch);
        cameraSwitch.setTextColor(Color.WHITE);
        cameraSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
                } else {
                    setCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
                }
            }
        });
        cameraSwitch.setEnabled(false);
        currentCameraId = NO_CAMERA;
        surfaceView = (SurfaceView) findViewById(R.id.phototakeractivity_surfaceview);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            private boolean used = false;

            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (!used) {
                    used = false;
                    cameraSwitch.setEnabled(true);
                    if (cameraSwitch.isChecked()) {
                        setCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
                    } else {
                        setCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
                    }
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                String selectedImagePath = getPath(selectedImageUri);
                Toast toast = Toast.makeText(context, selectedImagePath, Toast.LENGTH_LONG);
                toast.show();
                try {
                    PhotoFilteringActivity.image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    Intent intent = new Intent(context, PhotoFilteringActivity.class);
                    context.startActivity(intent);
                } catch (Exception ignored) {

                }
            }
        }
    }

    public String getPath(Uri uri) {
        if( uri == null ) {
            Toast toast = Toast.makeText(context, "Failed to get file", Toast.LENGTH_LONG);
            toast.show();
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (currentCameraId != NO_CAMERA) {
            startCamera(currentCameraId);
        }
    }
}
