//package com.streamstech.droid.mshtb.io;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.google.gson.stream.JsonReader;
//import java.io.IOException;
//import java.io.StringReader;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.FormBody;
//import okhttp3.OkHttpClient;
//import okhttp3.OkHttpClient.Builder;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import com.streamstech.droid.mshtb.activity.ProductFragement;
//import com.streamstech.droid.mshtb.data.ServerResponse;
//import com.streamstech.droid.mshtb.data.persistent.DatabaseManager;
//import com.streamstech.droid.mshtb.data.persistent.Product;
//import com.streamstech.droid.mshtb.data.persistent.ProductDTO;
//import com.streamstech.droid.mshtb.data.persistent.ProductDao;
//import com.streamstech.droid.mshtb.data.persistent.Task;
//import com.streamstech.droid.mshtb.data.persistent.TaskDTO;
//import com.streamstech.droid.mshtb.data.persistent.TaskDao;
//import com.streamstech.droid.mshtb.util.MSHTBApplication;
//import com.streamstech.droid.mshtb.util.ProductUpdateListener;
//import com.streamstech.droid.mshtb.util.UpdateType;
//
//public class BackgroundDownloader {
//
//    private ProductUpdateListener[] listeners = new ProductUpdateListener[2];
//    private static BackgroundDownloader instance;
//
//    public static BackgroundDownloader getInstance() {
//        if (instance == null)
//            instance = new BackgroundDownloader();
//        return instance;
//    }
//
//    private BackgroundDownloader() {
//        instance = this;
//    }
//
//    public void destroy()
//    {
//        listeners = null;
//        instance = null;
//    }
//
//    public void updateProduct(final boolean reloadall)
//    {
//        long maximumid = -1;
//        if (!reloadall) {
//            List<Product> savedResponse = DatabaseManager.getInstance().getSession()
//                    .getProductDao().queryBuilder()
//                    .limit(1).orderDesc(ProductDao.Properties.Id)
//                    .list();
//            if (!savedResponse.isEmpty()) {
//                maximumid = ((Product) savedResponse.get(0)).getId().longValue();
//            }
//        }
//        String jsonURL = Paths.SERVER_URL + Paths.PRODUCT;
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//
//        RequestBody formBody = new FormBody.Builder()
//                .add("sessionid", DatabaseManager.getInstance().getServerSession())
//                .add("lastproductid", String.valueOf(maximumid))
//                .build();
//
//        System.out.println("ProductActivity Request: " + formBody.toString());
//        client.newCall(new Request.Builder().url(jsonURL).post(formBody).build()).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, okhttp3.Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String strResponse = response.body().string();
//                    System.out.println("ProductActivity Response" + strResponse);
//                    try {
//                        ServerResponse serverResponse = (ServerResponse) new Gson()
//                                .fromJson(new JsonReader(new StringReader(strResponse)), (Type) ServerResponse.class);
//                        if (serverResponse.isSuccess()) {
//                            String allProducts = new Gson().toJson(serverResponse.getResult());
//                            List<ProductDTO> list = (List<ProductDTO>) new Gson()
//                                    .fromJson(allProducts,
//                                            new TypeToken<ArrayList<ProductDTO>>() {
//                                            }.getType());
//                            int size = list.size();
//                            ProductDao dao = DatabaseManager.getInstance().getSession()
//                                    .getProductDao();
//                            if (reloadall)
//                                dao.deleteAll();
//
//                            for (ProductDTO productDTO : list) {
//                                Product product = new Product(productDTO.getId());
//                                product.setDescription(productDTO.getDescription());
//                                product.setImageurl(Paths.SERVER_URL + productDTO.getImages());
//                                product.setProductname(productDTO.getProduct_name());
//                                product.setProducttype(productDTO.getProduct_type());
//                                product.setSkuid(productDTO.getProduct_id());
//                                product.setStatus(productDTO.getStatus());
//                                product.setQrcode(productDTO.getQr_string());
//
//                                product.setCustomerid(Long.getLong(productDTO.getCustomer_id(), 0));
//                                product.setCustomername(productDTO.getCustomer_name());
//                                product.setLongitude(Double.valueOf(productDTO.getLongi()));
//                                product.setLatitude(Double.valueOf(productDTO.getLati()));
//                                product.setSellingdate(productDTO.getSelling_date());
//                                product.setPurchasedate(productDTO.getPurchase_date());
//                                product.setSoldto(productDTO.getSold_to());
//                                product.setTags(productDTO.getTags());
//
//                                dao.insertOrReplace(product);
//                            }
//                            if (size > 0 && listeners[0] != null)
//                                listeners[0].productUpdated(UpdateType.ALL, null);
//                        }
//                    } catch (Exception exp) {
//                        exp.printStackTrace();
//                    }
//                }
//            }
//        });
//    }
//
//    public void updateTasks(final boolean reloadall)
//    {
//        long maximumid = 0;
//        if (!reloadall) {
//            List<Task> savedResponse = DatabaseManager.getInstance().getSession()
//                    .getTaskDao().queryBuilder()
//                    .limit(1).orderDesc(TaskDao.Properties.Id)
//                    .list();
//            if (!savedResponse.isEmpty()) {
//                maximumid = ((Task) savedResponse.get(0)).getId().longValue();
//            }
//        }
//        String jsonURL = Paths.SERVER_URL + Paths.TASK;
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//
//        String serverSession = DatabaseManager.getInstance().getServerSession();
//        RequestBody formBody = new FormBody.Builder()
//                .add("sessionid", serverSession)
//                .add("lastid", String.valueOf(maximumid))
//                .build();
//
//        System.out.println("Task Activity Request: " + formBody.toString());
//        client.newCall(new Request.Builder().url(jsonURL).post(formBody).build()).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, okhttp3.Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String strResponse = response.body().string();
//                    System.out.println("Task Response" + strResponse);
//                    try {
//                        ServerResponse serverResponse = (ServerResponse) new Gson()
//                                .fromJson(new JsonReader(new StringReader(strResponse)), (Type) ServerResponse.class);
//                        if (serverResponse.isSuccess()) {
//                            String allProducts = new Gson().toJson(serverResponse.getResult());
//                            List<TaskDTO> list = (List<TaskDTO>) new Gson()
//                                    .fromJson(allProducts,
//                                            new TypeToken<ArrayList<TaskDTO>>() {
//                                            }.getType());
//                            int size = list.size();
//                            TaskDao dao = DatabaseManager.getInstance().getSession()
//                                    .getTaskDao();
//                            if (reloadall)
//                                dao.deleteAll();
//
//                            for (TaskDTO taskDTO : list) {
//                                Task product = new Task(taskDTO.getId());
//                                product.setDescription(taskDTO.getDescription());
//                                product.setImageurl(Paths.SERVER_URL + taskDTO.getImage_url());
//                                product.setCompleted(taskDTO.getIs_completed() == 1 ? true : false);
//                                product.setDescriptionmust(taskDTO.getDescription_required() == 1 ? true : false);
//                                product.setImagemust(taskDTO.getImg_required() == 1 ? true : false);
//                                product.setStartdate(taskDTO.getCreate_date());
//                                product.setEnddate(taskDTO.getUpdate_date());
//                                product.setFeedback(taskDTO.getFeedback());
//                                if (taskDTO.getLatitude() == null || taskDTO.getLatitude().trim().equals(""))
//                                    product.setLatitude(-999);
//                                else
//                                    product.setLatitude(Double.parseDouble(taskDTO.getLatitude()));
//
//                                if (taskDTO.getLongitude() == null || taskDTO.getLongitude().trim().equals(""))
//                                    product.setLongitude(-999);
//                                else
//                                    product.setLongitude(Double.parseDouble(taskDTO.getLongitude()));
//
//                                product.setProductid(taskDTO.getProduct_id());
//                                product.setPublished(taskDTO.getIs_completed() == 1 ? true : false);
//                                dao.insertOrReplace(product);
//                            }
//                            if (size > 0 && listeners != null)
//                                listeners[1].taskUpdated(UpdateType.ALL, null);
//                        }
//                    } catch (Exception exp) {
//                        exp.printStackTrace();
//                    }
//                }
//            }
//        });
//    }
//
//    public void hodai(long productid) {
//        System.out.println("ProductActivity ID: " + productid);
//    }
//
//    public void getSingleProduct(long productid) {
//
//        String jsonURL = Paths.SERVER_URL + Paths.PRODUCT_DETAILS;
//        OkHttpClient client = new OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//
//        RequestBody formBody = new FormBody.Builder()
//                .add("sessionid", DatabaseManager.getInstance().getServerSession())
//                .add("productid", String.valueOf(productid))
//                .build();
//
//        System.out.println("Single ProductActivity Request: " + formBody.toString());
//        client.newCall(new Request.Builder().url(jsonURL).post(formBody).build()).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, okhttp3.Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    String strResponse = response.body().string();
//                    System.out.println("Single ProductActivity Response" + strResponse);
//                    try {
//                        ServerResponse serverResponse = (ServerResponse) new Gson()
//                                .fromJson(new JsonReader(new StringReader(strResponse)), (Type) ServerResponse.class);
//                        if (serverResponse.isSuccess()) {
//                            String allProducts = new Gson().toJson(serverResponse.getResult());
//                            List<ProductDTO> list = (List<ProductDTO>) new Gson()
//                                    .fromJson(allProducts,
//                                            new TypeToken<ArrayList<ProductDTO>>() {
//                                            }.getType());
//                            int size = list.size();
//                            ProductDao dao = DatabaseManager.getInstance().getSession()
//                                    .getProductDao();
//
//                            Product product = null;
//                            for (ProductDTO productDTO : list) {
//                                product = new Product(productDTO.getId());
//                                product.setDescription(productDTO.getDescription());
//                                product.setImageurl(Paths.SERVER_URL + productDTO.getImages());
//                                product.setProductname(productDTO.getProduct_name());
//                                product.setProducttype(productDTO.getProduct_type());
//                                product.setSkuid(productDTO.getProduct_id());
//                                product.setStatus(productDTO.getStatus());
//                                product.setQrcode(productDTO.getQr_string());
//
//                                product.setCustomerid(Long.getLong(productDTO.getCustomer_id(), 0));
//                                product.setCustomername(productDTO.getCustomer_name());
//                                product.setLongitude(Double.valueOf(productDTO.getLongi()));
//                                product.setLatitude(Double.valueOf(productDTO.getLati()));
//                                product.setSellingdate(productDTO.getSelling_date());
//                                product.setPurchasedate(productDTO.getPurchase_date());
//                                product.setSoldto(productDTO.getSold_to());
//                                product.setTags(productDTO.getTags());
//
//                                dao.insertOrReplace(product);
//                            }
//                            if (size > 0 && listeners != null)
//                                listeners[0].productUpdated(UpdateType.SCANNED, product);
//                        }
//                    } catch (Exception exp) {
//                        exp.printStackTrace();
//                    }
//                }
//            }
//        });
//    }
//
//    public void setListener(ProductUpdateListener listener) {
//
//        if (listener instanceof ProductFragement)
//            listeners[0] = listener;
//        else
//            listeners[1] = listener;
//    }
//
//
//    public void notfityTaskPragement()
//    {
//        if (listeners[1] != null)
//            listeners[1].taskUpdated(UpdateType.ALL, null);
//    }
//
//}
