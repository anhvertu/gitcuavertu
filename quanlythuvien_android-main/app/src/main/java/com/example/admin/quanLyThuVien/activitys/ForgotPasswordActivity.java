package com.example.admin.quanLyThuVien.activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.quanLyThuVien.R;
import com.example.admin.quanLyThuVien.Retrofit2.APIUtils;
import com.example.admin.quanLyThuVien.Retrofit2.DataClient;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgotPasswordActivity extends AppCompatActivity {
    // Biến constant được dùng để định danh dữ liệu được truyền giữa các Activity
    public static final String EXTRA_DATA = "EXTRA_DATA";
    private static final int LAY_LAI_MAT_KHAU = 11;
    Button btnQuayLai, btnGui;
    EditText edtEmail;
    String email = "", taiKhoan = "", maXacThuc = "", key = "";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        anhXa();

        final Intent intent = getIntent();
        taiKhoan = intent.getStringExtra(LoginActivity.TAI_KHOAN);

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo một Intent mới để chứa dữ liệu trả về
                Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                // Truyền data vào intent
                intent1.putExtra(LoginActivity.TAI_KHOAN, taiKhoan);
                // Đặt resultCode là Activity.RESULT_OK to
                // thể hiện đã thành công và có chứa kết quả trả về
                setResult(Activity.RESULT_OK, intent1);
                // gọi hàm finish() để đóng Activity hiện tại và trở về MainActivity.
                finish();

            }
        });
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kiemTra()) {
                    if (isOnline()) {
//                        guiMail();
                        checkEmail();

                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, getResources().getString(R.string.khong_co_internet), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LAY_LAI_MAT_KHAU) {
            if (resultCode == RESULT_OK) {
                progressDialog.show();
                guiMail();
            }
        }
    }


    private void checkEmail() {
        progressDialog = new ProgressDialog(ForgotPasswordActivity.this, R.style.Theme_AppCompat_Light_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.dang_xu_ly));
        progressDialog.show();
        maXacThuc = getMaXacThuc();
        DataClient dataClient = APIUtils.getData();
        Call<ResponseBody> call = dataClient.CheckDoiMatKhau(email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String kq = "";
                try {
                    kq = response.body().string();
                    if (!kq.equals("fail")) {
//                        Toast.makeText(QuenMatKhauActivity.this, kq, Toast.LENGTH_SHORT).show();
                        taiKhoan = kq;
                        guiMail();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(ForgotPasswordActivity.this, getResources().getString(R.string.email_khong_ton_tai), Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ForgotPasswordActivity.this, getResources().getString(R.string.loi_thao_tac), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void guiMail() {
        new SendMail().execute();
    }


    protected String getMaXacThuc() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    private class SendMail extends AsyncTask<String, Integer, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            gui();
            return null;
        }
    }


    public void gui() {
        final String username = "quanlythuvientest@gmail.com";
        final String password = "tuankhai";
        maXacThuc = getMaXacThuc();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(username, password);
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from-email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Đổi mật khẩu tài khoản thư viện");
            message.setText("Chào " + taiKhoan + " , \n Đây mã xác thực đổi mật khẩu của bạn: \n\t"
                    + maXacThuc + "\n Nhập mã trên vào ô nhập mã xác thực trong ứng dụng của bạn, mọi thắc mắc chi tiết xin liên hệ số điện thoại: 01654230XXX hoặc gặp trực tiếp thủ thư");

//            MimeBodyPart messageBodyPart = new MimeBodyPart();
//
//            Multipart multipart = (Multipart) new MimeMultipart();
//
//
//            messageBodyPart = new MimeBodyPart();
//            String file = "path of file to be attached";
//            String fileName = "attachmentName";
//            DataSource source = (DataSource) new FileDataSource(file);
//            messageBodyPart.setDataHandler(new DataHandler((javax.activation.DataSource) source));
//            messageBodyPart.setFileName(fileName);
//            multipart.addBodyPart(messageBodyPart);
//
//            message.setContent((javax.mail.Multipart) multipart);

            Transport.send(message);

            progressDialog.dismiss();

            Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
            intent.putExtra("maXacThuc", maXacThuc);
            intent.putExtra("taiKhoan", taiKhoan);
            setResult(Activity.RESULT_OK, intent);
            startActivityForResult(intent, LAY_LAI_MAT_KHAU);

//            Intent intent =  new Intent(QuenMatKhauActivity.this,DoiMatKhauActivity.class);
//            intent.putExtra("maXacThuc",maXacThuc);
//            intent.putExtra("taiKhoan",taiKhoan);
//            startActivity(intent);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    //    public static MimeMessage createEmail(String to,
//                                          String from,
//                                          String subject,
//                                          String bodyText)
//            throws MessagingException {
//        Properties props = new Properties();
//        Session session = Session.getDefaultInstance(props, null);
//
//        MimeMessage email = new MimeMessage(session);
//
//        email.setFrom(new InternetAddress(from));
//        email.addRecipient(javax.mail.Message.RecipientType.TO,
//                new InternetAddress(to));
//        email.setSubject(subject);
//        email.setText(bodyText);
//        return email;
//    }
//    public static Message createMessageWithEmail(MimeMessage emailContent)
//            throws MessagingException, IOException {
//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
//        emailContent.writeTo(buffer);
//        byte[] bytes = buffer.toByteArray();
//        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
//        Message message = new Message();
//        message.setRaw(encodedEmail);
//        return message;
//    }
//
//
//
    private boolean kiemTra() {
        email = edtEmail.getText().toString().trim();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError(getString(R.string.email_khong_hop_le));
            return false;
        } else {
            edtEmail.setError(null);
        }
        return true;
    }

    private void anhXa() {
        btnGui = findViewById(R.id.btnGuiMatKhau);
        btnQuayLai = findViewById(R.id.btnQuayLaiMatKhau);
        edtEmail = findViewById(R.id.edtTaiKhoan);
    }
}
