package network;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.practicaltest02.Utilities;

public class ClientThread extends Thread {

    private String address ;
    private int port;
    private String url;
    private TextView responseTextView;

    private Socket socket;

    public ClientThread(int port, String url, TextView responseTextView) {
        this.address = "localhost";
        this.port = port;
        this.url= url;
        this.responseTextView = responseTextView;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e("abc", "[CLIENT THREAD] Could not create socket!");
                return;
            }
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e("abc", "[CLIENT THREAD] Buffered Reader / Print Writer are null!");
                return;
            }
            printWriter.println(url);
            printWriter.flush();
            String response;
            while ((response = bufferedReader.readLine()) != null) {
                final String finalizedResponse = response;
                responseTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        responseTextView.setText(finalizedResponse);
                    }
                });
            }
        } catch (IOException ioException) {
            Log.e("abc", "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (true) {
                ioException.printStackTrace();
            }
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e("abc", "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
                    if (true) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }

}
