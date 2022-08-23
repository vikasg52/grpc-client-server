package client;

import com.google.common.base.Stopwatch;
import com.test.grpc.User;
import com.test.grpc.User.LoginRequest;
import com.test.grpc.userGrpc;
import com.test.grpc.userGrpc.userBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class GRPCClient {
    ManagedChannel channel;

    userBlockingStub userStub;

    LoginRequest loginrequest;
    public Stopwatch stopWatch;

    @BeforeTest
    public void setup() {
        channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build();
        userStub = userGrpc.newBlockingStub(channel);
    }


    @Test(priority=1)
    public void loginWithCorrectUser_Password() {
        stopWatch=  Stopwatch.createStarted();

        loginrequest = LoginRequest.newBuilder().setUsername("Vikas").setPassword("Vikas").build();

        userStub.login(loginrequest);

        User.APIResponse response = userStub.login(loginrequest);

        System.out.println("Login API Response is:-" + response.getResponseCode() + "\n and Response message is:-" + response.getResponsemessage());

        stopWatch.stop();

        System.out.println("Time is:-"+stopWatch.elapsed(TimeUnit.MILLISECONDS));
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(response.getResponseCode() == 0, true);

        softAssert.assertEquals(response.getResponsemessage().equalsIgnoreCase("SUCCESS"), true);
    }

//    @BeforeMethod
//    public void setupStopwatch() {
//        stopWatch= new StopWatch();
//        stopWatch.start();
//    }
//
//    @AfterMethod
//    public void stopIt() {
//        stopWatch.stop();
//
//        System.out.println("Time is:-"+stopWatch.getTime());
//    }

    @Test(priority=0)
    public void loginWithInCorrectUser_Password() {
        stopWatch=  Stopwatch.createStarted();

        loginrequest = LoginRequest.newBuilder().setUsername("Vikas").setPassword("Vikas").build();

        userStub.login(loginrequest);

        User.APIResponse response = userStub.login(loginrequest);

        System.out.println("Login API Response is:-" + response.getResponseCode() + "\n and Response message is:-" + response.getResponsemessage());

        System.out.println("Time is:-"+stopWatch.elapsed(TimeUnit.MILLISECONDS));

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(response.getResponseCode() == 100, true);

        softAssert.assertEquals(response.getResponsemessage().equalsIgnoreCase("INVALID PASSWORD"), true);
    }
}
