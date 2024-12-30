package org.example.inventory.server;

import org.example.inventory.database.DatabaseInitializer;
import org.example.inventory.server.implementations.ProductDaoImpl;
import org.example.inventory.server.models.ActionProduct;
import org.example.inventory.server.models.Product;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApplication {
    private static final int PORT = 3333;

    public static void main(String[] args) {
        DatabaseInitializer.initializeDatabase();
        System.out.println("Running ...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started and waiting for connections on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connection accepted: " + clientSocket.getInetAddress());

                // Delegate the client handling to a new thread
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private ProductDaoImpl productDao = new ProductDaoImpl();
        private final Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())
            ) {
                // Read the Product object sent by the client
                ActionProduct actionProduct = (ActionProduct) input.readObject();
                System.out.println("Product received from client:");
                System.out.println("product : "+actionProduct.getProduct());
                System.out.println("action : "+ actionProduct.getAction());

                switch (actionProduct.getAction()){
                    case Create ->{
                        Product newProduct = productDao.save(actionProduct.getProduct());
                        System.out.println("newProduct : down");
                        System.out.println(newProduct);
                        output.writeObject(newProduct);
                        output.flush();
                        return;
                    }
                    case Update ->{
                        Product newProduct = productDao.update(actionProduct.getProduct());
                         output.writeObject(newProduct);
                        System.out.println("update Product"+ newProduct);
                        output.flush();
                        return;
                    }
                    case Delete ->{
                        System.out.println("try to remove product"+ actionProduct.getProduct().getId());
                        Boolean res =  productDao.remove(actionProduct.getProduct());
                        System.out.println("res :"+ res);
                        output.writeObject(res);
                        output.flush();
                        return;
                    }
                }

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error processing client request: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Error closing client socket: " + e.getMessage());
                }
            }
        }
    }
}
