package com.ocangjava.portail.service;

import com.ocangjava.portail.dto.MessageRequest;
import com.ocangjava.portail.model.Message;
import com.ocangjava.portail.model.Rental;
import com.ocangjava.portail.model.User;
import com.ocangjava.portail.repository.MessageRepository;
import com.ocangjava.portail.repository.RentalRepository;
import com.ocangjava.portail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    public Message createMessage(MessageRequest request) {
        User user = userRepository.findById(request.getUser_id())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Rental rental = rentalRepository.findById(request.getRental_id())
                .orElseThrow(() -> new RuntimeException("Location non trouvée"));

        Message message = new Message();
        message.setMessage(request.getMessage());
        message.setUser(user);
        message.setRental(rental);

        return messageRepository.save(message);
    }
}