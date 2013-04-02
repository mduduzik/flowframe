package org.flowframe.kernel.json.impl.transformer;

import flexjson.JSONContext;
import flexjson.Path;
import flexjson.PathExpression;

import flexjson.transformer.ObjectTransformer;

import java.util.List;

import org.flowframe.kernel.common.utils.CharPool;
import org.flowframe.kernel.common.utils.StringPool;
import org.flowframe.kernel.json.JSONTransformer;
import org.flowframe.kernel.json.impl.JSONIncludesManager;

import jodd.bean.BeanUtil;

public class FlexjsonObjectJSONTransformer
	extends ObjectTransformer implements JSONTransformer {

	@Override
	public void transform(Object object) {
		Class<?> type = resolveClass(object);

		List<PathExpression> pathExpressions =
			(List<PathExpression>)BeanUtil.getDeclaredProperty(
				getContext(), "pathExpressions");

		String path = _getPath();

		String[] excludes = _jsonIncludesManager.lookupExcludes(type);

		_exclude(pathExpressions, path, excludes);

		String[] includes = _jsonIncludesManager.lookupIncludes(type);

		_include(pathExpressions, path, includes);

		super.transform(object);
	}

	private void _exclude(
		List<PathExpression> pathExpressions, String path, String... names) {

		for (String name : names) {
			PathExpression pathExpression = new PathExpression(
				path.concat(name), false);

			if (!pathExpressions.contains(pathExpression)) {
				pathExpressions.add(pathExpression);
			}
		}
	}

	private String _getPath() {
		JSONContext jsonContext = getContext();

		Path path = jsonContext.getPath();

		List<String> paths = path.getPath();

		if (paths.isEmpty()) {
			return StringPool.BLANK;
		}

		StringBuffer sb = new StringBuffer(paths.size() * 2);

		for (String curPath : paths) {
			sb.append(curPath);
			sb.append(CharPool.PERIOD);
		}

		return sb.toString();
	}

	private void _include(
		List<PathExpression> pathExpressions, String path, String... names) {

		for (String name : names) {
			PathExpression pathExpression = new PathExpression(
				path.concat(name), true);

			if (!pathExpressions.contains(pathExpression)) {
				pathExpressions.add(0, pathExpression);
			}
		}
	}

	private static JSONIncludesManager _jsonIncludesManager =
		new JSONIncludesManager();

}