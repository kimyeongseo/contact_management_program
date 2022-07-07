import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

class MyData{ // 입력받은 정보를 저장할 객체 
	private String name;
	private int age;
	private String phoneNum;
	
	public MyData(String name, int age, String phoneNum) {
		this.name = name;
		this.age = age;
		this.phoneNum = phoneNum;
	}
	
	// 저장한 MyData 객체에서 name, age, phoneNum 변수의 값을 가져오는 getters
	public String getName() { return this.name; }
	public int getAge() { return this.age; }
	public String getPhoneNum() { return this.phoneNum; }
	
	// 콘솔에 찍힐 이름, 나이, 연락처를 리턴하는 String 메소드입니다.
	public String toString() { return name + "(" + age + ") " + phoneNum; }
}

public class Main {
	Scanner in = new Scanner(System.in);
	boolean mainMenu = true; // print_mene() 메소드에 전달할 인자변수

	// 콘솔에서 입력받은 정보를 저장할 ArrayList
	ArrayList<MyData> data = new ArrayList<MyData>();

	public static void main(String[] args) throws IOException {
		Main program = new Main();
		Scanner in = new Scanner(System.in);
		System.out.print("[연락처 관리 프로그램]\n\n");
		program.print_menu(program.mainMenu);
		
		try {
			program.loadFileData();
			} catch(NoSuchFileException e) {
	//			e.printStackTrace();
			}		
			while (true) {
			System.out.print("메뉴를 선택하세요: ");

			while (!in.hasNextInt()) { // 키보드 입력이 숫자가 아닐 경우 실행되는 반복문
				in.next();
				System.out.print("\n숫자를 입력해주세요: ");
			}
			int option = in.nextInt();

			if (option == 4) {
				break;
			}

			switch (option) {
			case 1:
				program.view_juso();
				program.print_menu(program.mainMenu);

				break;
			case 2:
				program.add_juso();
				program.print_menu(program.mainMenu);

				break;
			case 3:
				program.delete_juso();
				program.print_menu(program.mainMenu);

				break;
			default:
				System.out.print("잘못된 메뉴입니다.");
				break;
			}
		}

		// 프로그램을 종료시 파일에 연락처 목록 입력
		program.writeMyData();
	}
	

	// 연락처 목록을 String 객체로 return
	public String toString() {
		String ret = "";
		for (int i = 0; i < data.size(); i++) {
			int index = i + 1;
			ret += "[" + index + "] " + data.get(i).toString() + "\n";
		}
		return ret;
	}

	public void print_menu(boolean mainMenu) {
		// menu를 출력합니다.
		if (mainMenu) {
			System.out.println("\n=======Main Menu=======");
			System.out.print("\n1) 연락처 출력 \n2) 연락처 등록 \n3) 연락처 삭제 \n4) 끝내기\n\n");
			System.out.println("=======================\n");

		} else {
			System.out.println("\n=======Delete Menu======");
			System.out.println("\n1) 순번 삭제 \n2) 이름 삭제 \n3) 번호 삭제 \n4) 메인메뉴로 이동\n");
			System.out.println("=======================\n");
		}
	}
	
	public void writeMyData() {
		File file = new File("juso.txt");
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			fw.write(toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

		
	public void loadFileData() throws IOException {
		List<String> readData = Files.readAllLines(Paths.get("juso.txt"));
		StringTokenizer st;
		String[] arr;
		for (int i = 0; i < readData.size(); i++) {
			st = new StringTokenizer(
					readData.get(i).toString().replace("[", "").replace("]", "").replace("(", " ").replace(")", ""), " ");
			int index = 0;
			arr = new String[st.countTokens()];
			while (st.hasMoreTokens()) {
				arr[index] = st.nextToken();
				index++;
			}
			String name = arr[1];
			int age = Integer.parseInt(arr[2]);
			String phoneNum = arr[3];
			
			data.add(new MyData(name, age, phoneNum));
		}
	}

	public void view_juso() {
		// 등록된 데이터가 있는 경우에 연락처 목록을 출력합니다.
		checkData();

		for (int i = 0; i < data.size(); i++) {
			System.out.printf("[%d] %s\n", i + 1, data.get(i).toString());
		}
	}

	public void add_juso() {
		// 연락처를 등록합니다.
		System.out.print("\n이름 입력: ");
		String name = in.next();

		System.out.print("나이 입력: ");
		while (!in.hasNextInt()) {
			in.next();
			System.out.print("\n숫자를 입력해주세요: ");
		}
		int age = in.nextInt();

		System.out.print("번호 입력: ");
		String phoneNum = in.next();
		data.add(new MyData(name, age, phoneNum));
	}

	private boolean checkData() {
		// data가 입력됐는지 여부를 확인합니다.
		if (data.size() <= 0) {
			System.out.println("등록된 연락처가 없습니다. \n메인 메뉴로 돌아갑니다. \n");
			return false;
		}
		return true;
	}


	public void delete_juso() {
		// 등록된 연락처가 있는 경우에만 삭제 상세 메뉴를 출력합니다.
		while (checkData()) {
			print_menu(!mainMenu);
			System.out.print("메뉴를 선택하세요: ");

			while (!in.hasNextInt()) { // 키보드 입력이 숫자가 아닐 경우 실행되는 반복문
				in.next();
				System.out.print("\n숫자를 입력해주세요: ");
			}
			int option = in.nextInt();

			if (option == 4) {
				break;
			}

			switch (option) {
			case 1:
				System.out.print("삭제할 연락처의 순번을 입력해주세요: ");
				while (!in.hasNextInt()) { // 키보드 입력이 숫자가 아닐 경우 실행되는 반복문
					in.next();
					System.out.print("\n숫자를 입력해주세요: ");
				}
				int index = in.nextInt();
				if (index > data.size() || index <= 0) {
					System.out.printf("없는 순번(%d)을 입력하였습니다.\n메인화면으로 돌아갑니다\n", index);
				} else {
					data.remove(index - 1);
					System.out.printf("입력한 (%d)순번의 연락처를 삭제하였습니다.\n\n", index);

				}

				break;
			case 2:
				System.out.print("삭제할 연락처의 이름을 입력해주세요: ");
				String name = in.next();
				int count = 0;
				for (int i = 0; i < data.size(); i++) {
					if (name.equals(data.get(i).getName())) {
						data.remove(i);
						System.out.printf("입력한 (%s)이름의 연락처를 삭제하였습니다.\n\n", name);

						count++;
					}
				}
				if (count == 0) {
					System.out.printf("해당 이름(%s)의 연락처는 없습니다. \n\n", name);
				}

				break;
			case 3:
				System.out.print("삭제할 연락처의 번호를 입력해주세요: ");
				String phoneNum = in.next();
				count = 0;
				for (int i = 0; i < data.size(); i++) {
					if (phoneNum.equals(data.get(i).getPhoneNum())) {
						data.remove(i);
						System.out.printf("입력한 (%s)번호의 연락처를 삭제하였습니다.\n\n", phoneNum);

						count++;
					}
				}
				if (count == 0) {
					System.out.printf("해당 번호(%s)의 연락처는 없습니다.\n\n", phoneNum);
				}

				break;
			default:
				System.out.print("잘못된 세부 메뉴입니다.\n");
				break;
			}
		}
	}
}
