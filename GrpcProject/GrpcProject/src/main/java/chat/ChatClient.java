package chat;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class ChatClient {

    private final String HOST = "localhost";
    private final int PORT = 9090; 

    private final ManagedChannel channel;
    private final ChatServiceGrpc.ChatServiceBlockingStub blockingStub;
    private final Scanner scanner;

    public ChatClient() {
        // Establishing a connection to the gRPC server
        channel = ManagedChannelBuilder.forAddress(HOST, PORT)
                .usePlaintext()
                .build();
        blockingStub = ChatServiceGrpc.newBlockingStub(channel);
        scanner = new Scanner(System.in);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
    }

    // Sends a message from one user to another
    public void sendMessage(String senderId, String recipientId, String message) {
        Chat.MessageRequest request = Chat.MessageRequest.newBuilder()
                .setSenderId(senderId)
                .setRecipientId(recipientId)
                .setMessage(message)
                .build();

        Chat.MessageResponse response = blockingStub.sendMessage(request);
        System.out.println("Your message has been sent successfully. Status: " + response.getStatus());
    }

    // Retrieves messages for a specific user
    public void getMessagesForUser(String userId) {
        Chat.UserRequest request = Chat.UserRequest.newBuilder()
                .setUserId(userId)
                .build();

        Chat.MessagesResponse response = blockingStub.getMessagesForUser(request);
        if (response.getMessageCount() == 0) {
            System.out.println("You have no messages yet user " + userId);
        } else {
            System.out.println("Messages for user " + userId + ":");
            response.getMessageList().forEach(msg -> {
                System.out.println("Message ID: " + msg.getMessageId());
                System.out.println("Sender ID: " + msg.getSenderId());
                System.out.println("Message: " + msg.getMessage());
            });
        }
    }

    // Runs the chat application
    public void startChat() {
    	System.out.println("--- Chat started ---           at  port: " + PORT);
        boolean running = true;
        while (running) {
            System.out.println("\nTo send a message press 'S'\nTo retrieve messages press 'R'\nTo exit press 'E':");
            String choice = scanner.nextLine();
            switch (choice) {
                case "S":
                    sendMessagePrompt();
                    break;
                case "R":
                    retrieveMessagesPrompt();
                    break;
                case "E":
                	System.out.println("Exiting Chat . Program terminated.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice !! . Please try again: ");
            }
        }
    }

    // Prompts the user to enter details for sending a message
    private void sendMessagePrompt() {
        System.out.print("Your user ID: ");
        String senderId = scanner.nextLine();
        System.out.print("Recipient's user ID: ");
        String recipientId = scanner.nextLine();
        System.out.print("Enter your message: ");
        String message = scanner.nextLine();
        sendMessage(senderId, recipientId, message);
    }

    // Prompts the user to enter their user ID for retrieving messages
    private void retrieveMessagesPrompt() {
        System.out.print("Enter your user ID to retrieve messages: ");
        String userId = scanner.nextLine();
        getMessagesForUser(userId);
    }

    public static void main(String[] args) throws Exception {
        ChatClient client = new ChatClient();
        client.startChat();
        client.shutdown();
    }
}

