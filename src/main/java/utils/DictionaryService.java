package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;


@Service
public class DictionaryService {
	
	@Resource(name = "redisTemplate")
	private HashOperations<String, String, Object> hashOps;
	
	
//	public List<DictionaryItem> loadDict(String dictName, String... dictValsTemp) {
//		Map<String, Object> map = hashOps.entries(NamingUtil.genStoreKey(NamingUtil.sk_dicts_format,dictName));
//		List<DictionaryItem> rtnList = Lists.newArrayList();
//		ArrayList<String> dictVals = dictValsTemp==null?null:Lists.newArrayList(dictValsTemp);
//		boolean dictValsIsNull = dictVals==null;
//		for (Entry<String, Object> entry : map.entrySet()) {
//			rtnList.add(dictValsIsNull || dictVals.contains(entry.getKey())?new DictionaryItem(dictName, entry.getKey(), (String) entry.getValue()):null);
//		}
//		return rtnList;
//	}

}
