package com.duan.travelshare.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.duan.travelshare.MainActivity;
import com.duan.travelshare.R;
import com.duan.travelshare.firebasedao.FullUserDao;
import com.duan.travelshare.firebasedao.UserDao;
import com.duan.travelshare.model.FullUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class ShowUserFragment extends Fragment {
    private ImageView ivAvatar;
    private DatePickerDialog datePickerDialog;
    private UserDao userDao;
    private FullUserDao fullUserDao;
    private ShowDialog showDialog;
    private Button btnUpdate;
    static EditText name, cmnd, email, birthday, phone, address;
    private String namex, cmndx, emailx, birthdayx, phonex, addressx, link;
    private FullUser u;

    //Xin quyền chụp ảnh
    Uri image_uri;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;
    private String[] cameraPermission;
    private String[] storagePermission;

    public ShowUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_user, container, false);
        MainActivity.navigation.setVisibility(View.GONE);
        //Khi ấn nút back
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.tbTitle);
        ImageView back = toolbar.findViewById(R.id.tbBack);
        title.setText("THÔNG TIN TÀI KHOẢN");
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.navigation.setVisibility(View.VISIBLE);
                UserFragment userFragment = new UserFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, userFragment)
                        .commit();
            }
        });

        progressDialog = new ProgressDialog(getActivity());
        showDialog = new ShowDialog(getActivity());
        fullUserDao = new FullUserDao(getActivity());
        userDao = new UserDao(getActivity());
        ivAvatar = view.findViewById(R.id.ivAvatar);
        //Đổ dữ liệu
        u = UserFragment.list;

        //Hiển thị thông tin lên
        name = view.findViewById(R.id.tvFullName);
        cmnd = view.findViewById(R.id.tvCmnd);
        email = view.findViewById(R.id.tvEmail);
        birthday = view.findViewById(R.id.tvBirthday);
        phone = view.findViewById(R.id.tvPhone);
        address = view.findViewById(R.id.tvAddress);
        btnUpdate = view.findViewById(R.id.btnUpdateUser);

        //storage firebase
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://truyen-60710.appspot.com");
        if (!u.getLinkImage().matches("")) {
            Picasso.with(getContext()).load(u.getLinkImage()).into(ivAvatar);
        }

        //Ẩn edit email
        email.setEnabled(false);
        birthday.setFocusable(false);
        //Khi chọn vào ngày
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int d = calendar.get(Calendar.DAY_OF_MONTH);
                int m = calendar.get(Calendar.MONTH);
                int y = calendar.get(Calendar.YEAR);
                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay = "";
                        if (String.valueOf(month).length() == 1) {
                            ngay = dayOfMonth + "/" + "0" + (month + 1) + "/" + year;
                        } else {
                            ngay = dayOfMonth + "/" + (month + 1) + "/" + year;
                        }
                        birthday.setText(ngay);
                    }
                }, y, m, d);
                datePickerDialog.show();
            }
        });

        //Set user
        setUser();


        //Khai báo xin quyền
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //Khi chọn vào avatar
        ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickDialog();
            }
        });

        //Cập nhật thông tin người dùng
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namex = name.getText().toString();
                cmndx = cmnd.getText().toString();
                emailx = email.getText().toString();
                birthdayx = birthday.getText().toString();
                phonex = phone.getText().toString();
                addressx = address.getText().toString();
                //Invailed Form
                checkForm();
            }
        });
        return view;
    }


    private void showImagePickDialog() {
        String option[] = {"Camera", "Thư viện ảnh"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Bạn muốn up ảnh từ đâu?");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        pickFromCamera();
                    }
                }
                if (which == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragetPermission();
                    } else {
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }


    private void checkForm() {
        if (image_uri==null) {
            showDialog.toastInfo("Vui lòng thêm ảnh đại diện!");
        } else if (namex.isEmpty() || cmndx.isEmpty() || emailx.isEmpty() || birthdayx.isEmpty() || phonex.isEmpty() || addressx.isEmpty()) {
            showDialog.toastInfo("Các trường không được để trống!");
        } else if (namex.length() < 5) {
            showDialog.toastInfo("Tên phải có ít nhất 5 ký tự!");
        } else if (cmndx.length()!=9 && cmndx.length()!=12) {
            showDialog.toastInfo("Chứng minh nhân dân phải đủ 9 số hoặc căn cước công dân phải đủ 12 số!");
        } else if (!phonex.matches("[0-9]{10,11}")) {
            showDialog.toastInfo("Vui lòng nhập đúng số điện thoại!");
        } else if (addressx.length() < 10) {
            showDialog.toastInfo("Vui lòng nhập đầy đủ địa chỉ!");
        } else {
            insertImage(image_uri);
        }
    }


    //Set info lên user
    public void setUser() {
        if (MainActivity.position != -1) {
            name.setText(u.getUserName());
            cmnd.setText(u.getCmndUser());
            email.setText(u.getEmailUser());
            birthday.setText(u.getBirtdayUser());
            phone.setText(u.getPhoneUser());
            address.setText(u.getAddressUser());
        }
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragetPermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST_CODE);
    }

    private void pickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp Pic");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp Description");
        image_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccept = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccept = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccept && storageAccept) {
                        pickFromCamera();
                    } else {
                        showDialog.toastInfo("Không truy cập được vào camera!");
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean writeStorageAccpted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (writeStorageAccpted) {
                        pickFromGallery();
                    } else {
                        showDialog.toastInfo("Vui lòng bật quyền thư viện");
                    }
                }
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                image_uri = data.getData();
                Picasso.with(getActivity()).load(image_uri).into(ivAvatar);
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                Picasso.with(getActivity()).load(image_uri).into(ivAvatar);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void insertImage(Uri uri) {
        progressDialog.show();
        progressDialog.setMessage("Đang cập nhật...");
        final String filePathAndName = "image_" + u.getEmailUser();
        storageReference.child(filePathAndName).putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while ((!uriTask.isSuccessful())) ;
                Uri dowloadUri = uriTask.getResult();
                if (uriTask.isSuccessful()) {
                    FullUser fullUser = new FullUser(namex, cmndx, emailx, birthdayx, phonex, addressx, dowloadUri.toString());
                    fullUserDao.updateUser(fullUser);
                    progressDialog.dismiss();
                    showDialog.toastInfo("Cập nhật thành công!");
                } else {
                    progressDialog.dismiss();
                    showDialog.toastInfo("Cập nhật thất bại!");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}