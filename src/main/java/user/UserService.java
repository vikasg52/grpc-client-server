package user;
import com.test.grpc.User;
import com.test.grpc.userGrpc;
import io.grpc.stub.StreamObserver;

public class UserService extends userGrpc.userImplBase {

    @Override
    public void login(User.LoginRequest request, StreamObserver<User.APIResponse> responseObserver) {
       System.out.println("Inside the Login");

       String username = request.getUsername();
       String password = request.getPassword();
        User.APIResponse.Builder response = User.APIResponse.newBuilder();

        if(username.equals(password)){
        response.setResponseCode(0).setResponsemessage("SUCCESS");
       }

        else{
            response.setResponseCode(100).setResponsemessage("INVALID PASSWORD");
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

    @Override
    public void logout(User.Empty request, StreamObserver<User.APIResponse> responseObserver) {
        System.out.println("Inside the Logout");
    }
}
