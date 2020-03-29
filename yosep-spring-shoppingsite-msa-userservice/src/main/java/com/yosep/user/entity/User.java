package com.yosep.user.entity;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
	private String userId;
	private String password;
	private String name;
	private String email;
	private String phone;
	private String postCode;
	private String roadAddr;
	private String jibunAddr;
	private String extraAddr;
	private String detailAddr;
	private Timestamp user_rdate;
	private Timestamp user_udate;
}
