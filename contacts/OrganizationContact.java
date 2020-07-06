package contacts;

class OrganizationContact extends Contact {
    static final long serialVersionUID = 1L;
    private String address;

    OrganizationContact(String name, String address, String number) {
        super(name, number);
	    // super.isPerson = false;
	    this.address = address;
    }

    @Override
    public String toString() { return "Organization name: " + getName() + "\n" +
    						   "Address: " + address + "\n" + 
    						   super.toString(); }
    
    // String getAddress() {
    //     return address;
    // }

//    @Override
//    void setName(String name) {
//    	super.name = name;
//    }

    void setAddress(String address) {
        this.address = address;
    }
}
