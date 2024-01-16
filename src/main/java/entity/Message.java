package com.example.entity;

import javax.persistence.*;

@Entity
@Table(name="message")
public class Message {

     @Column (name="message_id")
     @Id @GeneratedValue
    private Integer message_id;

    @Column (name="posted_by")
    private Integer posted_by;

    @Column (name="message_text")
    private String message_text;

    @Column (name="time_posted_epoch")
    private Long time_posted_epoch;

    public Message(){
    }

    public Message(Integer posted_by, String message_text, Long time_posted_epoch) {
        this.posted_by = posted_by;
        this.message_text = message_text;
        this.time_posted_epoch = time_posted_epoch;
    }

    public Message(Integer message_id, Integer posted_by, String message_text, Long time_posted_epoch) {
        this.message_id = message_id;
        this.posted_by = posted_by;
        this.message_text = message_text;
        this.time_posted_epoch = time_posted_epoch;
    }

    public Integer getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Integer message_id) {
        this.message_id = message_id;
    }

    public Integer getPosted_by() {
        return posted_by;
    }

    public void setPosted_by(Integer posted_by) {
        this.posted_by = posted_by;
    }

    public String getMessage_text() {
        return message_text;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }

    public Long getTime_posted_epoch() {
        return time_posted_epoch;
    }

    public void setTime_posted_epoch(Long time_posted_epoch) {
        this.time_posted_epoch = time_posted_epoch;
    }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (message_id == null) {
			if (other.message_id != null)
				return false;
		} else if (!message_id.equals(other.message_id))
			return false;
		if (message_text == null) {
			if (other.message_text != null)
				return false;
		} else if (!message_text.equals(other.message_text))
			return false;
		if (posted_by == null) {
			if (other.posted_by != null)
				return false;
		} else if (!posted_by.equals(other.posted_by))
			return false;
		if (time_posted_epoch == null) {
			if (other.time_posted_epoch != null)
				return false;
		} else if (!time_posted_epoch.equals(other.time_posted_epoch))
			return false;
		return true;
	}

    @Override
    public String toString() {
        return "Message{" +
                "message_id=" + message_id +
                ", posted_by=" + posted_by +
                ", message_text='" + message_text + '\'' +
                ", time_posted_epoch=" + time_posted_epoch +
                '}';
    }


}