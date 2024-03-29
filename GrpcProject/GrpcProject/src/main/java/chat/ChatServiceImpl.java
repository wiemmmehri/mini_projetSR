package chat;

import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {

    private final Map<String, Map<String, Chat.Message>> userMessages = new HashMap<>();

    @Override
    public void sendMessage(Chat.MessageRequest request, StreamObserver<Chat.MessageResponse> responseObserver) {
        String messageId = UUID.randomUUID().toString();
        String senderId = request.getSenderId();
        String recipientId = request.getRecipientId();
        String messageContent = request.getMessage();

        userMessages.putIfAbsent(recipientId, new HashMap<>());
        Map<String, Chat.Message> messagesForRecipient = userMessages.get(recipientId);

        Chat.Message message = Chat.Message.newBuilder()
                .setMessageId(messageId)
                .setSenderId(senderId)
                .setMessage(messageContent)
                .build();

        messagesForRecipient.put(messageId, message);

        Chat.MessageResponse response = Chat.MessageResponse.newBuilder()
                .setMessageId(messageId)
                .setStatus("Message sent successfully")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getMessagesForUser(Chat.UserRequest request, StreamObserver<Chat.MessagesResponse> responseObserver) {
        String userId = request.getUserId();
        Chat.MessagesResponse.Builder responseBuilder = Chat.MessagesResponse.newBuilder();

        if (userMessages.containsKey(userId)) {
            Map<String, Chat.Message> messagesForUser = userMessages.get(userId);
            messagesForUser.values().forEach(responseBuilder::addMessage);
            userMessages.remove(userId);
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}
