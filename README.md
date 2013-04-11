HttpWrapper
===========
Simple HttpWrapper to make requests to websites and keep sessions.

```Java
HttpWrapper wrapper = new HttpWrapper();
try {
    wrapper.setUrl("http://localhost/test");
    wrapper.addParameter("var", "variable");
    wrapper.setType(HttpWrapper.Type.Ajax);
    wrapper.setAction(HttpWrapper.Action.GET);
    System.out.println(wrapper.request());

	//Parameters are cleaned up after a request

    wrapper.setType(HttpWrapper.Type.Normal);
    wrapper.addParameter("var", "variable");
    wrapper.setAction(HttpWrapper.Action.POST);
    System.out.println(wrapper.request());


    //Easy to use with other CRUD requests
	//HttpWrapper.Action.GET
	//HttpWrapper.Action.POST
	//HttpWrapper.Action.PUT
	//HttpWrapper.Action.DELETE

	//Easy with laravel Input::json()
	//HttpWrapper.Type.Normal
	//HttpWrapper.Type.Json
	//HttpWrapper.Type.Ajax
	//HttpWrapper.Type.Json_Ajax

	//If type Json_Ajax or Json is set
	wrapper.setJson("{\"var\": \"variable\"}");

} catch (MalformedURLException ex) {
    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
} catch (IOException ex) {
    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
}
```