package com.fb.finstagram.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.fb.finstagram.R;
import com.fb.finstagram.model.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.IOException;

public class ComposeFragment extends AppCompatActivity {

    EditText etDescription;
    Button btnSubmit;
    Button btnPicture;
    ImageView ivPicture;
    public final String APP_TAG = "ComposeFragment";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_fragment);

        etDescription = (EditText) findViewById(R.id.etComposeDescription);
        btnSubmit = (Button) findViewById(R.id.btnComposeSubmit);
        btnPicture = (Button) findViewById(R.id.btnComposePicture);
        ivPicture = (ImageView) findViewById(R.id.ivPicture);

        //queryPosts();

    }
/*    private void queryPosts(){
        // think of this as data being stored into a list
        ParseQuery<Post>postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include("user");
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e==null){
                    for (int i=0; i<objects.size();i++){
                        Log.d("HomeActivity","Post["+i+"]= "+ objects.get(i).getDescription()+ " Username= "+objects.get(i).getUser().getUsername());
                    }
                }else{
                    e.printStackTrace();
                }
            }
        });
    }*/

    public void onClickSubmit(View view){
        String description = etDescription.getText().toString();
        ParseUser user = ParseUser.getCurrentUser();
        // accidentally taps on submit or if clicks take picture and does not take picture
        if (photoFile == null || ivPicture.getDrawable() == null ){
            Log.e(APP_TAG,"No photo to submit");
            Toast.makeText(ComposeFragment.this, "No photo", Toast.LENGTH_LONG).show();
        }else{
            savePost(description, user, photoFile);
        }

    }

    private void savePost(String description, ParseUser parseUser, File photoFile){
        Post post = new Post();
        post.setDescription(description);
        post.setUser(parseUser);
        post.setImage(new ParseFile(photoFile));

        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null){
                    Log.d("ComposeFragment", "Success while saving");
                    etDescription.setText("");
                    ivPicture.setImageResource(0);
                }else{
                    Log.d("ComposeFragment","Error while saving");
                    e.printStackTrace();
                    return;
                }
            }
        });
    }

    public void onClickPicture(View view){
        launchCamera();
    }

    public void launchCamera(){
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);
        // wrap File object into a content provider required for API >= 24
        Uri fileProvider = FileProvider.getUriForFile(ComposeFragment.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        // If you call startActivityForResult() using an intent that no app can handle, your app will crash. So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }
        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                ivPicture.setImageBitmap(rotateBitmapOrientation(photoFile.getAbsolutePath()));
            } else {
                // Result was a failure: if you exit out of picture prematurely (TODO ASK: what's diff w/ photoFile == null )
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Rotates picture
    public Bitmap rotateBitmapOrientation(String photoFilePath) {
        // Create and configure BitmapFactory
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoFilePath, bounds);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(photoFilePath, opts);
        // Read EXIF Data
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(photoFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
        // Rotate Bitmap
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        return rotatedBitmap;
    }

}
