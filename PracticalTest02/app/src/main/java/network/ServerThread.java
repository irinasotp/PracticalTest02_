package network;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import cz.msebera.android.httpclient.client.ClientProtocolException;

public class ServerThread extends Thread {

    private int port = 0;
    private ServerSocket serverSocket = null;


    public ServerThread(int port) {
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException ioException) {
            Log.e("abc", "An exception has occurred: " + ioException.getMessage());
            if (true) {
                ioException.printStackTrace();
            }
        }
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

  //  public synchronized void setData(String city, WeatherForecastInformation weatherForecastInformation) {
  //      this.data.put(city, weatherForecastInformation);
  //  }

  //  public synchronized HashMap<String, WeatherForecastInformation> getData() {
  //      return data;
   // }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Log.i("abc", "[SERVER THREAD] Waiting for a client invocation...");
                Socket socket = serverSocket.accept();
                Log.i("abc", "[SERVER THREAD] A connection request was received from " + socket.getInetAddress() + ":" + socket.getLocalPort());
                CommunicationThread communicationThread = new CommunicationThread(this, socket);
                communicationThread.start();
            }
        } catch (ClientProtocolException clientProtocolException) {
            Log.e("abc", "[SERVER THREAD] An exception has occurred: " + clientProtocolException.getMessage());
            if (true) {
                clientProtocolException.printStackTrace();
            }
        } catch (IOException ioException) {
            Log.e("abc", "[SERVER THREAD] An exception has occurred: " + ioException.getMessage());
            if (true) {
                ioException.printStackTrace();
            }
        }
    }

    public void stopThread() {
        interrupt();
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException ioException) {
                Log.e("abc", "[SERVER THREAD] An exception has occurred: " + ioException.getMessage());
                if (true) {
                    ioException.printStackTrace();
                }
            }
        }
    }

}