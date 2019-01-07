

package com.company.program.resource.common.cache;


import com.company.program.resource.resource.ResourceServer;
import com.company.program.resource.resource.common.cache.Cache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@ActiveProfiles("local")
@RunWith(SpringRunner.class)
@SpringBootTest (classes = {ResourceServer.class})
public class CacheTest {
	
	@Autowired
	private Cache cache;
	
	@Test
	public void testSet() {
		cache.set("cache1111", "cache1111v");
	}
	
	
	
}
