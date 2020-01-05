package com.chat.persistence.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author gdimitrova
 */
@Entity(name = "friend_requests")
@Table(name = "friend_requests")
public class FriendRequestDto extends AbstractDto {

    public final static String SENDER = "sender";

    public final static String RECEIVER = "receiver";

    public final static String SENDER_COLUMN = "sender_id";

    public final static String RECEIVER_COLUMN = "receiver_id";

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = SENDER_COLUMN)
    private UserDto sender;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = RECEIVER_COLUMN)
    private UserDto receiver;

    @Enumerated(EnumType.STRING)
    @Column
    private FriendRequestStatusDto status;

    public FriendRequestDto() {
        //Hibernate
    }

    public FriendRequestDto(UserDto sender, UserDto receiver, FriendRequestStatusDto status) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
    }

    public UserDto getSender() {
        return sender;
    }

    public void setSender(UserDto sender) {
        this.sender = sender;
    }

    public UserDto getReceiver() {
        return receiver;
    }

    public void setReceiver(UserDto receiver) {
        this.receiver = receiver;
    }

    public FriendRequestStatusDto getStatus() {
        return status;
    }

    public void setStatus(FriendRequestStatusDto status) {
        this.status = status;
    }

}
