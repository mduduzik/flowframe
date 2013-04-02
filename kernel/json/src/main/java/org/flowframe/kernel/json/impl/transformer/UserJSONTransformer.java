package org.flowframe.kernel.json.impl.transformer;

import org.flowframe.kernel.common.utils.StringPool;



public class UserJSONTransformer extends FlexjsonObjectJSONTransformer {

	@Override
	public void transform(Object object) {
/*		User user = (User)object;

		boolean hidePrivateUserData = true;

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker != null) {
			long userId = permissionChecker.getUserId();

			if (user.getUserId() == userId) {
				hidePrivateUserData = false;
			}
		}

		if (hidePrivateUserData) {
			user.setPasswordUnencrypted(StringPool.BLANK);
			user.setReminderQueryQuestion(StringPool.BLANK);
			user.setReminderQueryAnswer(StringPool.BLANK);
			user.setEmailAddress(StringPool.BLANK);
			user.setFacebookId(0);
			user.setComments(StringPool.BLANK);
		}

		super.transform(object);*/
	}

}