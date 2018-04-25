package masterung.androidthai.in.th.ungshop.utility;

public class MyConstance {

    private String urlAddUserString = "http://androidthai.in.th/bru/addUser.php";
    private String urlGetUserString = "http://androidthai.in.th/bru/getAllUser.php";
    private String urlGetFoodString = "http://androidthai.in.th/bru/getAllFood.php";
    private String urlDeleteFoodString = "http://androidthai.in.th/bru/deleteFood.php";
    private String urlEditPriceString = "http://androidthai.in.th/bru/editPrice.php";

    private String[] columnFoodStrings = new String[]{"id", "Category", "NameFood",
            "Price", "Detail", "ImagePath"};

    public String getUrlEditPriceString() {
        return urlEditPriceString;
    }

    public String getUrlDeleteFoodString() {
        return urlDeleteFoodString;
    }

    public String[] getColumnFoodStrings() {
        return columnFoodStrings;
    }

    public String getUrlGetFoodString() {
        return urlGetFoodString;
    }

    public String getUrlGetUserString() {
        return urlGetUserString;
    }

    public String getUrlAddUserString() {
        return urlAddUserString;
    }
}
