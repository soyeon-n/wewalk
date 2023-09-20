package com.spring.boot.user;

import lombok.Getter;

//클래스를 enum으로 바꿔줌
//Getter만 필요함
@Getter
public enum UserRole {

	//시큐리티 권한 코드는 접두사 ROLE_로 시작함
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	
	private String value;
	
	UserRole(String value) {
		this.value = value;
	}
	
}
/*
[ENUM]

enum은 열거 자료형(enumerated type)이라고 부른다. 
열거형은 서로 연관된 상수들의 집합을 class로 만들고 enum으로 변경시킴
','로 구분

enum Fruit{
    APPLE, PEACH, BANANA;
}
enum Company{
    GOOGLE, APPLE, ORACLE;
}

//enum Fruit에 들어있는 값을 Fruit.APPLE로 넣어주면 type에 APPLE이 들어감
Fruit type = Fruit.APPLE;

switch(type){
   case APPLE:
        System.out.println("사과입니다");
        break;
   case PEACH:
        System.out.println("복숭아입니다");
        break;
   case BANANA:
        System.out.println("바나나입니다");
        break;
}
*/