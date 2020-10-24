/**
 * coded by Pawan Singh Harariya
 */
package com.anvay.pawan.wholeseller.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.anvay.pawan.wholeseller.R;
import com.anvay.pawan.wholeseller.models.Product;
import com.anvay.pawan.wholeseller.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class AddProductActivity extends AppCompatActivity {
    private String category, productId, firebaseId, name, brand, details, sku, sla, origin,
            mName, mAddress, mContact, parentCat, childCat;
    private int stockQuantity, minQuantity, maxQuantity;
    private double price, gst, discount = 0.00;
    private EditText nameText, brandText, priceText, stockQuantityText, detailsText, discountText, skuText, slaText, originText, gstText,
            mNameText, mAddressText, mContactText, parentText, childText, minQuantityText, maxQuantityText;
    private Spinner gstSpinner;
    private ImageView imageView;
    private final int GALLERY = 700;
    private final int CAMERA = 8001;
    private Button addProductButton;
    private Bitmap newImage;
    private View addImage, loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getUI();
        productId = UUID.randomUUID().toString();
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        firebaseId = sharedPreferences.getString(Constants.FIREBASE_ID, null);
        category = bundle.getString(Constants.CATEGORY_KEY);
        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameText.getText().toString();
                brand = brandText.getText().toString();
                details = detailsText.getText().toString();
                sla = slaText.getText().toString();
                sku = skuText.getText().toString();
                origin = originText.getText().toString();
                mName = mNameText.getText().toString();
                mAddress = mAddressText.getText().toString();
                String productPrice = priceText.getText().toString();
                String productStockQ = stockQuantityText.getText().toString();
                String productMinQ = minQuantityText.getText().toString();
                String productMaxQ = maxQuantityText.getText().toString();
                String productGst = gstSpinner.getSelectedItem().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(brand) || TextUtils.isEmpty(productPrice) || TextUtils.isEmpty(productStockQ)
                        || TextUtils.isEmpty(details) || TextUtils.isEmpty(sla) || TextUtils.isEmpty(origin) || TextUtils.isEmpty(mName)
                        || TextUtils.isEmpty(mAddress) || TextUtils.isEmpty(productMinQ) || TextUtils.isEmpty(productMaxQ)
                        || TextUtils.isEmpty(productGst) || TextUtils.isEmpty(sku) || newImage == null)
                    Toast.makeText(AddProductActivity.this, "Enter all details", Toast.LENGTH_SHORT).show();
                else {
                    price = Double.parseDouble(productPrice);
                    gst = Double.parseDouble(productGst);
                    minQuantity = Integer.parseInt(productMinQ);
                    maxQuantity = Integer.parseInt(productMaxQ);
                    stockQuantity = Integer.parseInt(productStockQ);
                    String productDiscount = discountText.getText().toString();
                    if (!TextUtils.isEmpty(productDiscount))
                        discount = Double.parseDouble(productDiscount);
                    mContact = mContactText.getText().toString();
                    parentCat = parentText.getText().toString();
                    childCat = childText.getText().toString();
                    saveImage();
                }
            }
        });
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }

    private void chooseImage() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(AddProductActivity.this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallery();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int RESULT_CANCELED = 404;
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(AddProductActivity.this.getContentResolver(), contentURI);
                    imageView.setImageBitmap(bitmap);
                    newImage = bitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AddProductActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            if (data != null) {
                Bitmap thumbnail = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                imageView.setImageBitmap(thumbnail);
                newImage = thumbnail;
            }
        }
    }

    private void saveImage() {
        loading.setVisibility(View.VISIBLE);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference spaceRef = storageReference.child(firebaseId);
        String fileName = productId;
        final StorageReference imageRef = spaceRef.child(fileName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        newImage.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] data = baos.toByteArray();
        final UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        addProduct(uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddProductActivity.this, "Upload Failed", Toast.LENGTH_LONG).show();
                        loading.setVisibility(View.GONE);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddProductActivity.this, "Upload Failed" + e.getMessage() + e.getCause(), Toast.LENGTH_LONG).show();
                loading.setVisibility(View.GONE);
            }
        });
    }

    private void addProduct(String imageUrl) {
        Product product = new Product(productId, firebaseId, name, brand, details, category, imageUrl, sku, sla, origin,
                mName, mAddress, mContact, parentCat, childCat, stockQuantity, gst, minQuantity, maxQuantity, price, discount);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(Constants.PRODUCTS_PATH + "/" + category).document(productId).set(product)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddProductActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddProductActivity.this, "Error uploading data", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                    }
                });
    }

    private void getUI() {
        nameText = findViewById(R.id.name_text);
        priceText = findViewById(R.id.price_text);
        brandText = findViewById(R.id.brand_text);
        stockQuantityText = findViewById(R.id.stock_quantity_text);
        detailsText = findViewById(R.id.details_text);
        discountText = findViewById(R.id.discount_text);
        skuText = findViewById(R.id.sku_text);
        slaText = findViewById(R.id.sla_text);
        originText = findViewById(R.id.origin_text);
        mNameText = findViewById(R.id.mname_text);
        mAddressText = findViewById(R.id.maddress_text);
        mContactText = findViewById(R.id.mcontact_text);
        parentText = findViewById(R.id.parent_text);
        childText = findViewById(R.id.child_text);
        gstText = findViewById(R.id.gst_text);
        minQuantityText = findViewById(R.id.min_quantity_text);
        maxQuantityText = findViewById(R.id.max_quantity_text);
        gstSpinner = findViewById(R.id.gst_spinner);
        imageView = findViewById(R.id.image_view);
        addImage = findViewById(R.id.add_image);
        addProductButton = findViewById(R.id.add_product_button);
        loading = findViewById(R.id.loading);
    }
}