package bussiness;

public class Student {
	String name;
	String gioitinh;
	String avatarUrl;
	String nam;
	String khoa;
	String ngaysinh;
	String sdt;
	String mssv;
	String email;
	String youtube;
	String diachi;
	String fb;
	String thanhtich;
	String quequan;
	String daymon;
	String giongnoi;
	String thongtinkhac;
	String order="1";
	
	public Student(String name, String gioitinh, String avatarUrl, String nam,
			String khoa, String ngaysinh, String sdt, String mssv,
			String email, String youtube, String diachi, String fb,
			String thanhtich, String quequan, String daymon, String giongnoi,
			String thongtinkhac) {
		super();
		this.name = name;
		this.gioitinh = gioitinh;
		this.avatarUrl = avatarUrl;
		this.nam = nam;
		this.khoa = khoa;
		this.ngaysinh = ngaysinh;
		this.sdt = sdt;
		this.mssv = mssv;
		this.email = email;
		this.youtube = youtube;
		this.diachi = diachi;
		this.fb = fb;
		this.thanhtich = thanhtich;
		this.quequan = quequan;
		this.daymon = daymon;
		this.giongnoi = giongnoi;
		this.thongtinkhac = thongtinkhac;
	}
	public String buildParamPost() {
		StringBuilder param = new StringBuilder();
		param.append("name="+name);
		param.append("&gioitinh="+gioitinh);
		param.append("&namhoc="+nam);
		param.append("&nganhhoc="+khoa);
		param.append("&birthday="+ngaysinh);
		param.append("&dienthoai="+sdt);
		param.append("&mssv="+mssv);
		param.append("&email="+email);
		param.append("&youtobe="+youtube);
		param.append("&diachi="+diachi);
		param.append("&facebook="+fb);
		param.append("&thanhtich="+thanhtich);
		param.append("&quequan="+quequan);
		param.append("&monhoc[]="+daymon);
		param.append("&giongnoi="+giongnoi);
		param.append("&thongtinkhac="+thongtinkhac);
		param.append("&order="+order);
				
		return param.toString();
	}
}
