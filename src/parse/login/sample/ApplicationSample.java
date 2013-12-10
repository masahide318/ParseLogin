package parse.login.sample;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseTwitterUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Takahata
 * Date: 2013/09/03
 * Time: 11:00
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationSample extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Parseの初期化
        Parse.initialize(this,"ParseのApllication ID","ParseのClient key");
        //Twitterを使う場合
        ParseTwitterUtils.initialize("TwitterのConsumer key","TwitterのConsumer secret");
        //Facebookを使う場合
        ParseFacebookUtils.initialize("FacebookのAppID");
    }
}
