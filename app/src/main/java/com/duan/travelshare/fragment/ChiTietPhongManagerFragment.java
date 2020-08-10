package com.duan.travelshare.fragment;

import android.Manifest;
import android.app.AlertDialog;
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
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.duan.travelshare.MainActivity;
import com.duan.travelshare.R;
import com.duan.travelshare.firebasedao.PhongDao;
import com.duan.travelshare.model.ChiTietPhong;
import com.duan.travelshare.model.FullUser;
import com.duan.travelshare.model.HinhPhong;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class ChiTietPhongManagerFragment extends Fragment {
    private ImageView phong, user, save, call, messenger;
    private LinearLayout star;
    private TextView tenPhong, giaPhong, tenUser, emailUser, moTa;
    private Button xem, datPhong;
    ShowDialog showDialog;
    private ImageView h1, h2, h3;
    private int chooseImage = 0;
    Uri image_uri;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 500;
    private String[] cameraPermission;
    private String[] storagePermission;
    ArrayList<HinhPhong> listHinh = new ArrayList<>();
    private ArrayList<Uri> listHinhPhong = new ArrayList<>();
    private ArrayList<String> listImageFireBase = new ArrayList<>();
    private PhongDao phongDao;
    Boolean checkLink = false;

    public ChiTietPhongManagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chi_tiet_phong, container, false);
        //Ẩn navigation

        MainActivity.navigation.setVisibility(View.GONE);
        showDialog = new ShowDialog(getActivity());
        //Nhạn object
        Bundle bundle = getArguments();
        final ChiTietPhong chiTietPhong = (ChiTietPhong) bundle.getSerializable("list");
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://truyen-60710.appspot.com");

        //Khai báo
        progressDialog = new ProgressDialog(getActivity());
        phong = view.findViewById(R.id.ivPhong);
        user = view.findViewById(R.id.ivUser);
        save = view.findViewById(R.id.ivSave);
        call = view.findViewById(R.id.ivCall);
        messenger = view.findViewById(R.id.ivMessenger);
        star = view.findViewById(R.id.lnStar);
        tenPhong = view.findViewById(R.id.tvTenPhong);
        giaPhong = view.findViewById(R.id.tvGiaphong);
        tenUser = view.findViewById(R.id.tvUser);
        emailUser = view.findViewById(R.id.tvEmailP);
        moTa = view.findViewById(R.id.tvMota);
        xem = view.findViewById(R.id.btnXemUser);
        datPhong = view.findViewById(R.id.btnDatPhongChiTiet);

        if (!chiTietPhong.getImgPhong().get(0).isEmpty()) {
            Picasso.with(getActivity()).load(chiTietPhong.getImgPhong().get(0)).into(phong);
        } else {
            phong.setImageResource(R.drawable.phongtro);
        }
        if (!chiTietPhong.getFullUser().getLinkImage().isEmpty()) {
            Picasso.with(getActivity()).load(chiTietPhong.getFullUser().getLinkImage()).into(user);
        } else {
            user.setImageResource(R.drawable.userimg);
        }

        tenPhong.setText(chiTietPhong.getTenPhong());
        giaPhong.setText(chiTietPhong.getGiaPhong());
        tenUser.setText(chiTietPhong.getFullUser().getUserName());
        emailUser.setText(chiTietPhong.getFullUser().getEmailUser());
        moTa.setText(chiTietPhong.getMoTaPhong());

        //Sửa phòng
        datPhong.setText("CHỈNH SỬA");
        datPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera();
                listImageFireBase.clear();
                listHinhPhong.clear();
                listHinh.clear();
                phongDao = new PhongDao(getActivity());
                final String key = chiTietPhong.getIdPhong();
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.add_room);
                dialog.setCancelable(true);
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (dialog != null && dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                final EditText tenPhong, giaPhong, diaChi, moTa;
                Button btnThem, btnNhapLai;
                TextView title;
                title = dialog.findViewById(R.id.tvTitle);
                title.setText("SỬA PHÒNG");
                btnThem = dialog.findViewById(R.id.btnTPThem);
                btnNhapLai = dialog.findViewById(R.id.btnTPNhapLai);
                tenPhong = dialog.findViewById(R.id.edtTPTieuDe);
                giaPhong = dialog.findViewById(R.id.edtTPGiaPhong);
                diaChi = dialog.findViewById(R.id.edtTPDiaChi);
                moTa = dialog.findViewById(R.id.edtTPMoTaChiTietPhong);
                h1 = dialog.findViewById(R.id.ivH1);
                h2 = dialog.findViewById(R.id.ivH2);
                h3 = dialog.findViewById(R.id.ivH3);

                tenPhong.setText(chiTietPhong.getTenPhong());
                giaPhong.setText(chiTietPhong.getGiaPhong());
                diaChi.setText(chiTietPhong.getDiaChiPhong());
                moTa.setText(chiTietPhong.getMoTaPhong());
                btnThem.setText("SỬA");
                if (!chiTietPhong.getImgPhong().isEmpty()) {
                    switch (chiTietPhong.getImgPhong().size()) {
                        case 1:
                            Picasso.with(getActivity()).load(chiTietPhong.getImgPhong().get(0)).into(h1);
                            listHinh.add(new HinhPhong("h1", Uri.parse(chiTietPhong.getImgPhong().get(0))));
                            break;
                        case 2:
                            Picasso.with(getActivity()).load(chiTietPhong.getImgPhong().get(0)).into(h1);
                            Picasso.with(getActivity()).load(chiTietPhong.getImgPhong().get(1)).into(h2);
                            listHinh.add(new HinhPhong("h1", Uri.parse(chiTietPhong.getImgPhong().get(0))));
                            listHinh.add(new HinhPhong("h2", Uri.parse(chiTietPhong.getImgPhong().get(1))));
                            break;
                        case 3:
                            Picasso.with(getActivity()).load(chiTietPhong.getImgPhong().get(0)).into(h1);
                            Picasso.with(getActivity()).load(chiTietPhong.getImgPhong().get(1)).into(h2);
                            Picasso.with(getActivity()).load(chiTietPhong.getImgPhong().get(2)).into(h3);
                            listHinh.add(new HinhPhong("h1", Uri.parse(chiTietPhong.getImgPhong().get(0))));
                            listHinh.add(new HinhPhong("h2", Uri.parse(chiTietPhong.getImgPhong().get(1))));
                            listHinh.add(new HinhPhong("h3", Uri.parse(chiTietPhong.getImgPhong().get(2))));
                            break;
                    }
                }

                h1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chooseImage = 1;
                        showImagePickDialog();
                    }
                });

                h2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chooseImage = 2;
                        showImagePickDialog();
                    }
                });

                h3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chooseImage = 3;
                        showImagePickDialog();
                    }
                });


                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final FullUser user = MainActivity.fullUserOne;
                        final String ten = tenPhong.getText().toString();
                        final String gia = giaPhong.getText().toString();
                        final String dc = diaChi.getText().toString();
                        final String mot = moTa.getText().toString();
                        progressDialog.show();
                        progressDialog.setMessage("Đang cập nhật...");

                        //Đổ listHinh vào
                        if (!listHinh.isEmpty()) {
                            for (int i = 0; i < listHinh.size(); i++) {
                                listHinhPhong.add(listHinh.get(i).getLinkHinh());
                            }
                        }
                        Boolean checkImage = true;
                        //So sánh ảnh có bị thay đổi không
                        if (listHinh.size() == chiTietPhong.getImgPhong().size()) {
                            for (int i = 0; i < listHinh.size(); i++) {
                                if (!listHinh.get(i).getLinkHinh().toString().equalsIgnoreCase(chiTietPhong.getImgPhong().get(i))) {
                                    checkImage = false;
                                    break;
                                }
                            }
                        }
                        if (checkImage == true) {
                            for (int i = 0; i < listHinh.size(); i++) {
                                listImageFireBase.add(listHinh.get(i).getLinkHinh().toString());
                            }
                            ChiTietPhong chiTietPhong = new ChiTietPhong(key, ten, gia, dc, mot, listImageFireBase, user);
                            //Thêm phòng
                            phongDao.updatePhong(chiTietPhong);
                            progressDialog.dismiss();
                            dialog.dismiss();
                        } else {
                            for (int i = 0; i < listHinhPhong.size(); i++) {
                                Uri IndividualImage = listHinhPhong.get(i);
                                storageReference.child(key + "h" + (i + 1)).putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                        while ((!uriTask.isSuccessful())) ;


                                        if (uriTask.isSuccessful()) {
                                            Uri dowloadUri = uriTask.getResult();
                                            listImageFireBase.add(String.valueOf(dowloadUri));
                                            if (listImageFireBase.size() == listHinhPhong.size()) {
                                                ChiTietPhong chiTietPhong = new ChiTietPhong(key, ten, gia, dc, mot, listImageFireBase, user);
                                                //Thêm phòng
                                                phongDao.updatePhong(chiTietPhong);
                                                progressDialog.dismiss();
                                                dialog.dismiss();
                                            }
                                        } else {
                                            progressDialog.dismiss();
                                            dialog.dismiss();
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


                    }
                });

                //Button nhập lại
                btnNhapLai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        tenPhong.setText("");
                        giaPhong.setText("");
                        diaChi.setText("");
                        moTa.setText("");
                    }
                });

                dialog.show();
            }
        });

        //Tắt các tính năng chỉ dành cho khách hàng
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.equals(save) || view.equals(call) || view.equals(messenger) || view.equals(star) || view.equals(xem)) {
                    showDialog.show("Chỉ khách hàng mới dùng được các chức năng này!");
                }
            }
        };
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog=new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_callphone);
                dialog.setCancelable(true);
                TextView phone=dialog.findViewById(R.id.number);
                //  phone.setText("0962280703");
                phone.setText(chiTietPhong.getFullUser().getUserName());
                dialog.show();
            }
        });
        save.setOnClickListener(listener);
        messenger.setOnClickListener(listener);
        star.setOnClickListener(listener);
        xem.setOnClickListener(listener);


        //Khi ấn nút back
        //Toolbar
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.tbTitle);
        ImageView back = toolbar.findViewById(R.id.tbBack);
        title.setText("CHI TIẾT PHÒNG");
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.navigation.setVisibility(View.VISIBLE);
                ManegerPhongThueFragment userFragment = new ManegerPhongThueFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame, userFragment)
                        .commit();
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
                        showDialog.show("Không truy cập được vào camera!");
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
                        showDialog.show("Vui lòng bật quyền thư viện");
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
                chooseImage();
            }
            if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                chooseImage();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void chooseImage() {
        switch (chooseImage) {
            case 1:
                checkLink = false;
                Picasso.with(getActivity()).load(image_uri).into(h1);
                if (!listHinh.isEmpty()) {
                    for (int i = 0; i < listHinh.size(); i++) {
                        HinhPhong hinhPhong = listHinh.get(i);
                        if (hinhPhong.getIdHinh().matches("h1")) {
                            checkLink = true;
                            listHinh.set(i, new HinhPhong("h1", image_uri));
                            break;
                        }
                    }
                }
                if (!checkLink) {
                    listHinh.add(new HinhPhong("h1", image_uri));
                }
                break;
            case 2:
                checkLink = false;
                Picasso.with(getActivity()).load(image_uri).into(h2);
                if (!listHinh.isEmpty()) {
                    for (int i = 0; i < listHinh.size(); i++) {
                        HinhPhong hinhPhong = listHinh.get(i);
                        if (hinhPhong.getIdHinh().matches("h2")) {
                            checkLink = true;
                            listHinh.set(i, new HinhPhong("h2", image_uri));
                            break;
                        }
                    }
                }
                if (!checkLink) {
                    listHinh.add(new HinhPhong("h2", image_uri));
                }
                break;
            case 3:
                checkLink = false;
                Picasso.with(getActivity()).load(image_uri).into(h3);
                if (!listHinh.isEmpty()) {
                    for (int i = 0; i < listHinh.size(); i++) {
                        HinhPhong hinhPhong = listHinh.get(i);
                        if (hinhPhong.getIdHinh().matches("h3")) {
                            checkLink = true;
                            listHinh.set(i, new HinhPhong("h3", image_uri));
                            break;
                        }
                    }
                }
                if (!checkLink) {
                    listHinh.add(new HinhPhong("h3", image_uri));
                }
                break;
        }
    }

    private void camera() {
        //Khai báo xin quyền
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }
    private void requestContactsPermissions(){


    };
}