package org.example;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Arrays.asList;
import static java.util.Collections.*;
import static junit.framework.Assert.assertTrue;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    @Override
    public String toString() {
        return super.toString();
    }

    public static void main(String[] args) {

        final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
        final String url = "http://10.1.0.36:8015",
                db = "prod_20230725",
                username = "admin",
                password = "$$tapidor!!";

        final XmlRpcClient models = new XmlRpcClient() {{
            setConfig(new XmlRpcClientConfigImpl() {{
                try {

                    setServerURL(new URL(String.format("%s/xmlrpc/2/object", url)));

                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }});
        }};

        try {

            common_config.setServerURL(new URL(String.format("%s/xmlrpc/2/common", url)));
            System.out.println(common_config.getServerURL().toString());

            int uid = (int) models.execute(common_config, "authenticate", asList(db, username, password, emptyMap()));
//            System.out.println("uid" + " " + uid);

//            final List ids = asList((Object[]) models.execute("execute_kw", asList(db, uid, password, "res.partner", "search",
//                    asList(asList(asList("is_company", "=", true))), new HashMap() {{
//                        put("limit", 1);
//                    }})));
//            System.out.println(ids);

//            chariot_info(getChariottList(models, db, uid, password));
//            getEmployeeList(models, db, uid, password);
//            getEmployee(models, db, uid, password);

            getEmployeeId(models, db, uid, password);
//            getVisiteusId(models, db, uid, password);
//            getChariotId(models, db, uid, password);

//            getChariottList(models, db, uid, password);
//            getVisiteusId(models, db, uid, password);

//            createOrder(models, db, uid, password);

//            chariot_info_android(getChariottList_android(models, db, uid, password));
//            final List<Object> read = getObjectList(models, db, uid, password);
//            ArrayList<String> list = new ArrayList<>();
//            list = visitues_into(read);
//            System.out.println(list.size());

        } catch (MalformedURLException e) {
            // Handle the case where the URL is malformed
            e.printStackTrace();
        } catch (XmlRpcException e) {
            throw new RuntimeException(e);
        }

    }

    private static List<Object> getChariottList_android(XmlRpcClient models, String db, int uid, String password) throws XmlRpcException {
        final List<Object> read = asList((Object[]) models.execute("execute_kw", asList(db, uid, password, "quality.control.order", "search_read",
                asList(asList()),
                new HashMap() {{
                    put("fields", asList("buggy_id"));
//                    put("limit", 1);
                }})));
//        System.out.println("chariot_list" + " " + read);
        return read;
    }

    public static ArrayList<HashMap<Object, Object>> chariot_info_android(List<Object> read) {

        ArrayList<String> visiteus_list = new ArrayList<>();
        ArrayList<HashMap<Object, Object>> list_of_new_map = new ArrayList<>();

        for (int item = 0; item < read.size(); item++) {
//            System.out.println(read.get(item));

            HashMap<Object, Object> map = new HashMap<>();
            map = (HashMap<Object, Object>) read.get(item);
            System.out.println("map_size" + " " + map);

            HashMap<Object, Object> map_recycler = new HashMap<>();

            Set keys = map.keySet();
            Iterator k = keys.iterator();
            for (Object key : map.keySet()) {
//                System.out.println("map_keys"+" "+key);
//
                if (String.valueOf(key).equals("buggy_id")) {
                    Object[] into_in_array;
                    Collection getValues = map.values();
                    Iterator i = getValues.iterator();
//                    into_in_array = (Object[]) i.next();
                    into_in_array = (Object[]) map.get(key);

                    map_recycler.put("id", into_in_array[0]);
                    map_recycler.put("chariot", into_in_array[1]);
                    list_of_new_map.add(map_recycler);
                    System.out.println("New_Map:" + "   " + list_of_new_map);

//                    visiteus_list.add((String) into_in_array[1]);
//                    System.out.println("buggy_id:" + "   " + into_in_array[0]);

                } else if (String.valueOf(key).equals("employee_id")) {
                    Object[] info_in_array;

                    info_in_array = (Object[]) map.get(key);
                    visiteus_list.add((String) info_in_array[1]);
//                    System.out.println("employee_id:" + "   " + ((String) info_in_array[1]).length());
                }
            }
        }
        return list_of_new_map;
    }

    private static int createOrder(XmlRpcClient models, String db, int uid, String password) throws XmlRpcException {
        final Integer id = (Integer) models.execute("execute_kw", asList(db, uid, password, "quality.control.order", "create",
                asList(new HashMap() {{
                    put("buggy_id", 5);
                    put("employee_id", 8142);
                    put("visiting_id", 51);

                }})));


        System.out.println("id_Creation" + " " + id);
        return id;
    }

    private static List<Object> getEmployeeList(XmlRpcClient models, String db, int uid, String password) throws XmlRpcException {
        final List<Object> read = asList((Object[]) models.execute("execute_kw", asList(db, uid, password, "hr.employee", "search_read",
                asList(asList(asList("recup_operator", "=", true))),
                new HashMap() {{
                    put("fields", asList("name"));
//                    put("limit", 1);
                }})));
//        System.out.println("employee_list" + " " + read);
        return read;
    }

    private static int getEmployeeId(XmlRpcClient models, String db, int uid, String password) throws XmlRpcException {
        int id = 0;
        ArrayList<String> visiteus_list = new ArrayList<>();
        final List<Object> read = asList((Object[]) models.execute("execute_kw", asList(db, uid, password, "hr.employee", "search_read",
                asList(asList(asList("recup_operator", "=", true))),
                new HashMap() {{
                    put("fields", asList("name", "barcode"));
//                    put("limit", 1);
                }})));
        System.out.println("employee_list" + " " + read);

        ArrayList<HashMap<Object, Object>> list_of_new_map = new ArrayList<>();

        for (int item = 0; item < read.size(); item++) {
            System.out.println(read.get(item));

            HashMap<Object, Object> map = (HashMap<Object, Object>) read.get(item);
            HashMap<Object, Object> map_recycler = new HashMap<>();

//            System.out.println("map_size" + " " + map.size());

            String code_brackit = "";
            String name = "";

            for (Object key : map.keySet()) {
//                System.out.println("map_keys"+" "+key);
                if (key.equals("barcode")) {

                    code_brackit = "[" + map.get(key) + "]";
                    System.out.println("code" + " " + code_brackit);
                }
                if (key.equals("name")) {

                    name = (String) map.get(key);
//                    code_name = code_name + name;
                    System.out.println("name:" + "   " + name);

                } else if (key.equals("id")) {
                    id = (int) map.get(key);
                    System.out.println("hada id:" + "   " + String.valueOf(id));
                }

            }
            map_recycler.put("name", code_brackit + name);
            map_recycler.put("id", id);
            list_of_new_map.add(map_recycler);
            System.out.println("list_map" + " " + list_of_new_map);

//            visiteus_list.add(code_brackit + name);
        }

//        System.out.println("employee" + " " + read);
        return id;
    }


    private static int getvisituesId(XmlRpcClient models, String db, int uid, String password) throws XmlRpcException {
        int id = 0;
        String name = "";
        final List<Object> read = asList((Object[]) models.execute("execute_kw", asList(db, uid, password, "quality.control.order", "search_read",
                asList(asList()),
                new HashMap() {{
                    put("fields", asList("buggy_id"));
//                    put("limit", 1);
                }})));
        System.out.println("chario_list" + " " + read);
        for (int item = 0; item < read.size(); item++) {
//            System.out.println(read.get(item));

            HashMap<Object, Object> map = new HashMap<>();
            map = (HashMap<Object, Object>) read.get(item);
//            System.out.println("map_size" + " " + map.size());

            for (Object key : map.keySet()) {
//                System.out.println("map_keys"+" "+key);

                if (key.equals("id")) {

                    id = (int) map.get(key);
                    System.out.println("id:" + "   " + id);

                } else if (key.equals("name")) {
                    name = (String) map.get(key);
                    System.out.println("name:" + "   " + name);
                }

            }
        }

//        System.out.println("employee" + " " + read);
        return id;
    }

    private static int getChariotId(XmlRpcClient models, String db, int uid, String password) throws XmlRpcException {
        int id = 0;
        String name = "";
        final List<Object> read = asList((Object[]) models.execute("execute_kw", asList(db, uid, password, "quality.control.order", "search_read",
                asList(asList()),
                new HashMap() {{
                    put("fields", asList("buggy_id"));
//                    put("limit", 1);
                }})));
        System.out.println("chariot_list" + " " + read);
        for (int item = 0; item < read.size(); item++) {
//            System.out.println(read.get(item));

            HashMap<Object, Object> map = new HashMap<>();
            map = (HashMap<Object, Object>) read.get(item);
//            System.out.println("map_size" + " " + map.size());

            ArrayList<Object> lis = new ArrayList<>();
            for (Object key : map.keySet()) {
//                System.out.println("map_keys"+" "+key);

                if (String.valueOf(key).equals("buggy_id")) {
                    Object[] into_in_array;
                    into_in_array = (Object[]) map.get(key);
                    id = (int) into_in_array[0];
                    name = (String) into_in_array[1];

                    System.out.println("buggy_id:" + "   " + id + " " + name);


                } else {
                    id = (int) map.get(key);
                    System.out.println("id:" + "   " + id);
                }

            }
        }

//        System.out.println("employee" + " " + read);
        return id;
    }


    public static ArrayList<String> employee_info(List<Object> read) {

        ArrayList<String> visiteus_list = new ArrayList<>();
        for (int item = 0; item < read.size(); item++) {
//            System.out.println(read.get(item));

            HashMap<Object, Object> map = new HashMap<>();
            map = (HashMap<Object, Object>) read.get(item);
//            System.out.println("map_size" + " " + map.size());

            Set keys = map.keySet();
            Iterator k = keys.iterator();
            for (Object key : map.keySet()) {
//                System.out.println("map_keys"+" "+key);
//
                Object[] into_in_array;
                Collection getValues = map.values();
                Iterator i = getValues.iterator();
                into_in_array = (Object[]) i.next();

//                    into_in_array =(Object[]) map.get(key);
                System.out.println("employee_id:" + "   " + into_in_array[1]);
            }
        }
        return visiteus_list;
    }


    private static List<Object> getChariottList(XmlRpcClient models, String db, int uid, String password) throws XmlRpcException {
        final List<Object> read = asList((Object[]) models.execute("execute_kw", asList(db, uid, password, "quality.control.order", "search_read",
                asList(asList()),
                new HashMap() {{
                    put("fields", asList("buggy_id"));
//                    put("limit", 1);
                }})));
        System.out.println("chariot_list" + " " + read);
        return read;
    }

    public static ArrayList<String> chariot_info(List<Object> read) {

        ArrayList<String> visiteus_list = new ArrayList<>();
        for (int item = 0; item < read.size(); item++) {
//            System.out.println(read.get(item));

            HashMap<Object, Object> map = new HashMap<>();
            map = (HashMap<Object, Object>) read.get(item);
//            System.out.println("map_size" + " " + map.size());

            Set keys = map.keySet();
            Iterator k = keys.iterator();
            for (Object key : map.keySet()) {
//                System.out.println("map_keys"+" "+key);
//
                if (String.valueOf(key).equals("buggy_id")) {
                    Object[] into_in_array;
                    Collection getValues = map.values();
                    Iterator i = getValues.iterator();
                    into_in_array = (Object[]) i.next();

//                    into_in_array =(Object[]) map.get(key);
                    System.out.println("buggy_id:" + "   " + into_in_array[1]);

                }
            }
        }
        return visiteus_list;
    }

    private static List<Object> getObjectList(XmlRpcClient models, String db, int uid, String password) throws XmlRpcException {
        final List<Object> read = asList((Object[]) models.execute("execute_kw", asList(db, uid, password, "quality.control.order", "search_read",
                asList(asList()),
                new HashMap() {{
                    put("fields", asList("visiting_id", "name", "employee_id", "order_id", "buggy_id", "start_date"));
                    put("limit", 2);
                }})));
//        System.out.println(read);
        return read;
    }

    public static ArrayList<String> visitues_into(List<Object> read) {

        ArrayList<String> visiteus_list = new ArrayList<>();
        for (int item = 0; item < read.size(); item++) {
//            System.out.println(read.get(item));

            HashMap<Object, Object> map = new HashMap<>();
            map = (HashMap<Object, Object>) read.get(item);
            System.out.println("map_size" + " " + map.size());

            Set keys = map.keySet();
            Iterator k = keys.iterator();
            for (Object key : map.keySet()) {
//                System.out.println("map_keys"+" "+key);
//
                if (String.valueOf(key).equals("visiting_id")) {
                    Object[] into_in_array;
                    Collection getValues = map.values();
                    Iterator i = getValues.iterator();
                    into_in_array = (Object[]) i.next();

//                    into_in_array =(Object[]) map.get(key);
                    System.out.println("visiting_id:" + "   " + into_in_array[1]);

                } else if (String.valueOf(key).equals("name")) {
                    System.out.println("name:" + "   " + map.get(key));

//                    System.out.println(map.get(key));

                } else if (String.valueOf(key).equals("employee_id")) {
                    Object[] employee_info;
//                    Collection getValues = map.values();
//                    Iterator i = getValues.iterator();
                    employee_info = (Object[]) map.get(key);
                    System.out.println("employee_id:" + "   " + employee_info[1]);

                } else if (String.valueOf(key).equals("buggy_id")) {
                    Object[] buggy_info;
//                    Collection getValues = map.values();
//                    Iterator i = getValues.iterator();
                    buggy_info = (Object[]) map.get(key);
                    System.out.println("buggy_id:" + "   " + buggy_info[1]);

                } else if (String.valueOf(key).equals("order_id")) {
                    Object[] order_info;
//                    Collection getValues = map.values();
//                    Iterator i = getValues.iterator();
                    order_info = (Object[]) map.get(key);
                    System.out.println("order_id:" + "   " + order_info[1]);

                } else if (String.valueOf(key).equals("start_date")) {
                    Object[] date_info;
//                    Collection getValues = map.values();
//                    Iterator i = getValues.iterator();
//                    date_info = (Object[]) map.get(key);
                    System.out.println("order_id:" + "   " + map.get(key));

                }


            }

//            while (k.hasNext()) {
//                System.out.println(k.next());
//            }
//        String name_machine ;
            Object[] into_in_array;
            Collection getValues = map.values();
            Iterator i = getValues.iterator();
            into_in_array = (Object[]) i.next();

            for (int j = 0; j <= 1; j++) {
                if (j == 1) {
                    visiteus_list.add((String) into_in_array[j]);
//                    System.out.println(visiteus_list);
                }
            }
//            System.out.println(visiteus_list.size());

            System.out.println("-----------------------------------------------");

        }


        return visiteus_list;


    }
}


//    final List<Object> read = asList((Object[]) models.execute("execute_kw", asList(db, uid, password, "quality.control.order", "search_read",
//            asList(asList()),
//            new HashMap() {{
//                put("fields", asList( "visiting_id"));
//                put("limit", 1);
//            }})));
//            System.out.println(read);
//
//                    HashMap<Object, Object> map = new HashMap<>();
//        map = (HashMap<Object, Object>) read.get(0);
//        System.out.println(map.size());
//
//        Set keys = map.keySet();
//        Iterator i = keys.iterator();
//        while (i.hasNext()) {
//        System.out.println(i.next());
//        }
//
//        Object [] into_in_array;
//        Collection getValues = map.values();
//        i = getValues.iterator();
//
//        while (i.hasNext()) {
//        into_in_array =(Object[]) i.next();
//        System.out.println(into_in_array.length);
//
//        for (Object item : into_in_array){
//        System.out.println(item);
//        }
//        break;
//        }


//            for (Object element : list) {
//                // Process each element here
//                System.out.println(element);
//            }

//            for (int j = 0; j<=1; j++){
//
//                String id ;
//                String name = null;
//                if (j==0){
//                    id = String.valueOf(j);
//                }else{
//                    name = String.valueOf(j);
//                }
//                System.out.println(name);
//            }

//            final List<Object> read = asList((Object[])models.execute("execute_kw", asList(db, uid, password, "quality.control.order", "search_read",
//                    asList(asList()),
//                    new HashMap() {{
//                        put("fields", asList("visiting_id"));
//                        put("limit", 1);
//                    }})));
//            System.out.println(read);


//            StringBuilder sb = new StringBuilder();
//            sb.append("{");
//            map.forEach((key, value) -> {
//                sb.append(key).append("=").append(value).append(", ");
//            });
//            sb.setLength(sb.length() - 2);
//            sb.append("}");
//
//            String result = sb.toString();
//            System.out.println("Result "+" "+result);
//
//
//            String[] arrOfStr = result.split("=", 2);
//
//            for (String a : arrOfStr)
//                System.out.println(a);

//            System.out.println(map);
//
//            System.out.println(visi);


//            final List search_read_visit = asList((Object[])models.execute("execute_kw", asList(db, uid, password, "mrp.workcenter", "search_read",
//                    asList(asList()),
//                    new HashMap() {{
//                        put("fields", asList("visiting_id"));
//                        put("limit", 1);
//                    }})));
//                    HashMap<String,Object> map =new HashMap<>();
//                    final List search_read2 = asList((Object[])models.execute("execute_kw", asList(db, uid, password, "quality.control.order", "search_read",
//                            asList(asList()),
//                            map.put("fields", asList("name", "visiting_id")),
//                            map.put("limit", 1)
//                    )));
//                    System.out.println(search_read2);
