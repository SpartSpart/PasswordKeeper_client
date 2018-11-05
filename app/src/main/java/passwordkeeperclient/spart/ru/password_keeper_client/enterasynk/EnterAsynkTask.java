package passwordkeeperclient.spart.ru.password_keeper_client.enterasynk;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

public class EnterAsynkTask extends AsyncTask<String, Void, Boolean> {

    private ProgressBar bar;
    private boolean processEnded = false;

    public void setProcessEnded(boolean processEnded) {
        this.processEnded = processEnded;
    }

    public EnterAsynkTask(ProgressBar bar) {
        this.bar = bar;
    }

    @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bar.setVisibility(View.VISIBLE);
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
//            while (!processEnded){
//                 }
//                 for (int i=0;i<1000000;i++){
//
//                 }
        return true;

        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            bar.setVisibility(View.INVISIBLE);
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//            if (result)
//                Toast.makeText(getApplicationContext(), "File sent", Toast.LENGTH_LONG).show();
//            else
//                Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_LONG).show();
//        }
    }


}
