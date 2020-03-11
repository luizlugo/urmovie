package mx.volcanolabs.urmovie;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

public class Utilities {
    public static Observable<Boolean> checkInternet() {
        return new Observable<Boolean>() {
            @Override
            protected void subscribeActual(@NonNull Observer<? super Boolean> observer) {
                try {
                    int timeoutMs = 1500;
                    Socket sock = new Socket();
                    SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

                    sock.connect(sockaddr, timeoutMs);
                    sock.close();

                    observer.onNext(true);
                } catch (IOException e) { observer.onNext(false); }
            }
        };
    }
}
