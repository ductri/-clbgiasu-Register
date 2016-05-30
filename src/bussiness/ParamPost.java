package bussiness;

public class ParamPost {
	private StringBuilder param = new StringBuilder();
	private boolean isFirst = true;
	public void add(String att, String value) {
		if (isFirst) {
			//name=NguyenTri&order=1"
			param.append(att+"="+value);
		} else {
			param.append("&"+att+"="+value);
		}
	}
	
	public String get() {
		return param.toString();
	}
}
