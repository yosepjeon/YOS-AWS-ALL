package com.yosep.website.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Order {
	private String orderId;
	private String productId;
	private String senderId;
	private String senderName;
	private String receiverName;
	private String phone;
	private String postCode;
	private String roadAddr;
	private String jibunAddr;
	private String extraAddr;
	private String detailAddr;
	private boolean isBuy;
}
