package com.votrihieu.web26t9.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "[User]") // Đặt tên bảng là "[User]" vì User là từ khóa trong SQL Server
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String fullname;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	private String phone;

	// Mối quan hệ một-nhiều với Product
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Product> products = new HashSet<>();

	// Mối quan hệ nhiều-nhiều với Category
	// Đây là bên sở hữu mối quan hệ (owning side)
	@ManyToMany
	@JoinTable(name = "UserCategory", // Tên bảng join bạn đã tạo
			joinColumns = @JoinColumn(name = "userid"), // Cột User ID trong bảng join
			inverseJoinColumns = @JoinColumn(name = "categoryid") // Cột Category ID trong bảng join
	)
	private Set<Category> categories = new HashSet<>();

	// Constructor bỏ qua id cho create
	public User(String fullname, String email, String password, String phone) {
		this.fullname = fullname;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}
}