package com.quchwe.gd.cms;

import com.quchwe.gd.cms.bean.SysUser;
import com.quchwe.gd.cms.repository.SysUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuchweCmsApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Mock
	SysUserRepository userRepository;

	@Test
	public void testUserUpdate(){


		SysUser user = new SysUser();
		user.setPhoneNumber("18014923897");
		user.setSex("男");


	}
}
