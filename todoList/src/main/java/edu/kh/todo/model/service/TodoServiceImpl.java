package edu.kh.todo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.todo.model.dao.TodoDAO;

// @Transactional 
// - 트랜잭션 처리를 수행하라고 지시하는 어노테이션
// - 정상 코드 수행 시 COMMIT
// - 기본값 : Service 내부 코드 수행 중 RuntimeException 발생 시 rollback
// rollbackFor 속성 : 어떤 예외가 발생했을 때 rollback 할 지 지정하는 속성
// Exception.class == 모든 예외 발생 시 rollback 하겠다

@Transactional(rollbackFor = Exception.class)
@Service  // 비즈니스 로직(데이터가공, 트랜잭션 처리등) 역할 명시 + Bean 등록
public class TodoServiceImpl implements TodoService{

	@Autowired // TodoDAO와 같은 타입/상속관계 Bean 의존성 주입(DI)
	private TodoDAO dao;

	@Override
	public String testTitle() {
		// TODO Auto-generated method stub
		return null;
	} 
	
	
	
	
	
	
	
	
}
