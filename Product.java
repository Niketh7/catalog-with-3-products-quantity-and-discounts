package niketh.bean;

//class create
public class Product {
	
	private String name;
	private double price;
	
//default constructure
	public Product() {
		super();
	}

//parameter constructure
	public Product(String name, double price) {
		super();
		this.name = name;
		this.price = price;
	}

//get and set
	public String getName() {
		return name;
	}

 
	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
	
	

}
