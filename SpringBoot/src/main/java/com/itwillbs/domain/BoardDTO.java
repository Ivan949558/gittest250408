package com.itwillbs.domain;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//Lombok 관련 이노테이션(라이브러리)
//@Setter, @Getter : Getter, Setter 메서드 생성
//@ToString : toString() 메서드 생성
//@ToString(exclude={변수명}) : 변수병 제외 toString() 메서드 생성
//@NonNull : null 체크 => NullPointException 예외발생
//@EqualsAndHashCode : equals(), hashCode() 메서드 생성
//@Builder : 빌더 패턴 이용 객체 생성
//@NoArgsConstructor : 파라미터가 없는 생성자(기본생성자) 생성
//@AllArgsConsturctor : 모든 파라미터가 있는 생성자 생성
//@RequiredArgsConstructor : 초기화 되지 않은 Final, @NonNull 붙은 필드 생성자 생성
//@Value : 불변(값이 변하지 않음, 변경할 수 없는) 클래스 생성
//@Data :  @Setter @Getter, @ToSring, 
//		   @EqualsAndHashCode, @RequiredArgsConstructor 합친 어노테이션
//@Log : log 자동변수 생성

//회원관련 데이터를 저장해서 전달
@Setter
@Getter
@ToString
public class BoardDTO {
	//객체지향 개념 특징 : 데이터 은닉(캡술화)
	//멤버변수 : 외부에 접근 못하게 막아줌(데이터 은닉, 접근 제어자 private)
	private int num;
	private String name;
	private String subject;
	private String content;
	private int readcount;
	private Timestamp rdate;
	private String file;
	
}
