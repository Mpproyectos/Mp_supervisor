package kreandoapp.mpsupervisores;

import kreandoapp.mpsupervisores.Notifications.MyResponse;
import kreandoapp.mpsupervisores.Notifications.Sender;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {

                  "Content-Type:application/json",
                  "Authorization:key=AAAAiWLOqh0:APA91bFtFw5v5GveJMqB14ah0P139jgSfGkI58bni5qtBcS0evSYcmqAaGlCIixWjjOq9bvOtrDTbwfK97sYG_IrfXfzEV8wJJUNtAF8CiMZAXfXCsikJM0NBMCSmffRat5zjmsJ2T3q"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
