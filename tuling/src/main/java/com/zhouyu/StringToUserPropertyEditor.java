package com.zhouyu;

import com.zhouyu.domain.User;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorSupport;

public class StringToUserPropertyEditor extends PropertyEditorSupport implements PropertyEditor {

	 @Override
	 public void setAsText(String text) throws IllegalArgumentException {
	  User user = new User();
	  user.setName(text);
	  this.setValue(user);
	 }
}