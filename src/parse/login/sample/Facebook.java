package parse.login.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionDefaultAudience;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.util.Arrays;

public class Facebook extends Activity {

    private Button btnReadPermissions = null;
    private Button btnPublishPermissions = null;
    private Button btnPost = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facebook_layout);
        btnReadPermissions = (Button) findViewById(R.id.btnReadPermission);
        btnReadPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestReadPermissions();
            }
        });

        btnPublishPermissions = (Button) findViewById(R.id.btnPublishStream);
        btnPublishPermissions.setEnabled(false);
        btnPublishPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPublishPermissions();
            }
        });

        btnPost = (Button) findViewById(R.id.btnPost);
        btnPost.setEnabled(false);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postToFacebook();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.getSession().onActivityResult(this, requestCode, resultCode, data);
        if( ParseFacebookUtils.getSession().getPermissions().contains("publish_stream") ){
            ParseFacebookUtils.saveLatestSessionData(ParseUser.getCurrentUser());
            btnPost.setEnabled(true);
        }
    }

    //read_permissionを取得する
    public void requestReadPermissions() {
        ParseFacebookUtils.logIn(
                this, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (err == null) {
                    Toast.makeText(getApplicationContext(), "認証完了", Toast.LENGTH_LONG).show();
                    btnPublishPermissions.setEnabled(true);
                }
            }
        });
    }

    //publish_streamのpermissionを取得する
    public void requestPublishPermissions() {
        Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this, Arrays.asList("publish_stream"));
        newPermissionsRequest.setDefaultAudience(SessionDefaultAudience.ONLY_ME);
        ParseFacebookUtils.getSession().requestNewPublishPermissions(newPermissionsRequest);
    }

    //Facebookへ「テスト」を投稿する
    public void postToFacebook() {
        Request request = Request.newStatusUpdateRequest(ParseFacebookUtils.getSession(), "テスト", new Request.Callback() {
            @Override
            public void onCompleted(Response response) {
                Toast.makeText(getApplicationContext(), " 投稿完了", Toast.LENGTH_LONG).show();
            }
        });
        request.executeAsync();
    }
}

