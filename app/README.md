# The Demo app

## Test 1

* https://github.com/hantsy/pestoapp?tab=readme-ov-file#apache-tomcat-10
* https://itnext.io/building-jakarta-ee-9-web-application-with-servlet-containers-b3acc50c8464


* install openjdk

* install maven:

```bash
choco install maven
```

* git clone the app code:

```bash

git clone git@github.com:hantsy/pestoapp.git


mvn clean package

```


Pour faire tourner l'applciation en local, exécuter:

```bash
mvn clean package cargo:run
```

Dès lors, vous pouvez:

* tester la WebUI à l'url
* et tester la REST API avec :

```bash

export DEMO_API_BASE_URL=${DEMO_API_BASE_URL:-'http://localhost:8080/pestoapp'}

export NOM_DUNE_PERSONNE_URL_ENCODED="CharlesDeGaulle"
export NOM_DUNE_PERSONNE_URL_ENCODED="JeanBaptisteLully"
export NOM_DUNE_PERSONNE_URL_ENCODED="Moli%C3%A8re"
curl -iv ${DEMO_API_BASE_URL}/api/greeting/${NOM_DUNE_PERSONNE_URL_ENCODED}

```

* Petite référence JAXRS pour les CRUD de ressources: https://gayerie.dev/udev-javaee/javaee_web/jaxrs.html



```bash

cd ${WHERE_YOU_GIT_CLONED}/app/pestoapp

mvn clean package

scp ./target/pestoapp.war pesto@192.168.1.16:~/deploy_tomcat


```


```bash
ssh pesto@192.168.1.16

cd ~/deploy_tomcat/

export DCK_IMG_TAG='10-jdk11-corretto'
export DCK_IMG_TAG='10-jdk21-temurin-noble'

# docker stop tomcat && docker rm tomcat

docker run --name tomcat -itd \
  --restart unless-stopped -p 8888:8080 \
  -v $PWD/pestoapp.war:/usr/local/tomcat/webapps/pestoapp.war \
  tomcat:${DCK_IMG_TAG}

```

SO I had to find a way to install OpenJDK in a matching version on my dev machien and inside the tomcat container:

* on my dev windows machine I found that `choco install microsoft-openjdk` will install an openjdk 21
* and I found a tomcat with jdk21 : `10-jdk21-temurin-noble`

Finally, I had to upgrade the version of the `jakarta.jakartaee-bom` artifact from `9.1.0` to `11.0.0` in the app `pom.xml`, and voilà.

http://192.168.1.16:8888/pesto/



```bash

# export DEMO_API_BASE_URL=${DEMO_API_BASE_URL:-'http://localhost:8080/pestoapp'}

unset DEMO_API_BASE_URL

export DEMO_API_BASE_URL=${DEMO_API_BASE_URL:-'http://192.168.1.16:8888/pestoapp'}

unset DEMO_API_BASE_URL
export DEMO_API_BASE_URL=${DEMO_API_BASE_URL:-'http://192.168.1.16:8888/pestoapp'}

export NOM_DUNE_PERSONNE_URL_ENCODED="CharlesDeGaulle"
export NOM_DUNE_PERSONNE_URL_ENCODED="JeanBaptisteLully"
export NOM_DUNE_PERSONNE_URL_ENCODED="Moli%C3%A8re"
curl -iv ${DEMO_API_BASE_URL}/api/greeting/${NOM_DUNE_PERSONNE_URL_ENCODED}


export NOM_D_UN_FRUIT='mangue'
export NOM_D_UN_FRUIT='citron'
export NOM_D_UN_FRUIT='papaye'
export NOM_D_UN_FRUIT='lanzones'

curl -iv ${DEMO_API_BASE_URL}/api/fruit/${NOM_D_UN_FRUIT}

export ID_D_UN_FRUIT="16568453468"
curl -iv ${DEMO_API_BASE_URL}/api/fruit/id/${ID_D_UN_FRUIT}

```

Worth noting now, and will be useful for our monitoring stack tests, I coded the app such that one of the endpoint has a query params which allows setting a lag or the response:

```bash
unset DEMO_API_BASE_URL
export DEMO_API_BASE_URL=${DEMO_API_BASE_URL:-'http://192.168.1.16:8888/pestoapp'}

export NOM_D_UN_FRUIT='lanzones'

export DESIRED_LAG_IN_MILLISECONDS="30"

curl -iv ${DEMO_API_BASE_URL}/api/fruit/${NOM_D_UN_FRUIT}?lagInMillisec=${DESIRED_LAG_IN_MILLISECONDS}


export DESIRED_LAG_IN_MILLISECONDS="300"

curl -iv ${DEMO_API_BASE_URL}/api/fruit/${NOM_D_UN_FRUIT}?lagInMillisec=${DESIRED_LAG_IN_MILLISECONDS}


export DESIRED_LAG_IN_MILLISECONDS="3000"

curl -iv ${DEMO_API_BASE_URL}/api/fruit/${NOM_D_UN_FRUIT}?lagInMillisec=${DESIRED_LAG_IN_MILLISECONDS}

```

## ANNEX: encountered issues

At this point, I could see that there is an issue with classloading the Classes:
* I can access without errors http://192.168.1.16:8888/pestoapp/hello.xhtml
* but when i type a message and click on the `Say Hello` submit button, I get this answer, which is pretty clear: 

```XML
<?xml version='1.0' encoding='UTF-8'?>
<partial-response><error><error-name>class jakarta.el.PropertyNotFoundException</error-name><error-message><![CDATA[See your server log for more information]]></error-message></error></partial-response>
```

And in the tomcat logs:

```log
Nov 02, 2025 9:03:04 AM com.sun.faces.context.AjaxExceptionHandlerImpl handlePartialResponseError
SEVERE: jakarta.el.PropertyNotFoundException: /hello.xhtml @20,15 value="#{hello.name}": Target Unreachable, identifier [hello] resolved to null
        at com.sun.faces.facelets.el.TagValueExpression.getType(TagValueExpression.java:64)
        at com.sun.faces.renderkit.html_basic.HtmlBasicInputRenderer.getConvertedValue(HtmlBasicInputRenderer.java:69)
        at jakarta.faces.component.UIInput.getConvertedValue(UIInput.java:1040)
        at jakarta.faces.component.UIInput.validate(UIInput.java:955)

```
I finally found the issue, by explicity declare servlets inside the `WEB-INF/web.xml` , here you go and now its very, very clear:

```log
SEVERE: Servlet [Jersey Web Application] in web application [/pestoapp] threw load() exception
java.lang.UnsupportedClassVersionError: com/example/GreetingResource has been compiled by a more recent version of the Java Runtime (class file version 61.0), this version of the Java Runtime only recognizes class file versions up to 55.0 (unable to load class [com.example.GreetingResource])

```

Some infos:

https://balusc.omnifaces.org/2013/10/how-to-install-cdi-in-tomcat.html

> Tomcat 10.0.x is the first version to be "Jakartified", i.e. it's using jakarta.* package instead of javax.* package for the API classes. It is using Servlet 5.0 API of Jakarta EE 9. Tomcat 10.1.x is the second Jakartified version, using Servlet 6.0 API of Jakarta EE 10.


https://mvnrepository.com/artifact/org.jboss.weld.servlet/weld-servlet-shaded/

