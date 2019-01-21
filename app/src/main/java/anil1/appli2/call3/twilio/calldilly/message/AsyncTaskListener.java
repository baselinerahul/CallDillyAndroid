package anil1.appli2.call3.twilio.calldilly.message;


public interface AsyncTaskListener {
    public void onPreExecute();

    public String doInBackground(Object... params);

    public String httpGetRequest(String urlPath);

    public String httpPostRequest(String urlPath, Object request);

    public void onPostExecute(String result);

    public void execute(Object... pararms);
}


