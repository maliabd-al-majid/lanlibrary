#lanlibrary

example on how to publish library on bintray using dokka.

## Steps

1- create android project.

2- under app module , add new module and select Android library.

![alt text](https://i.ibb.co/JkC6FGs/1.png)

![alt text](https://i.ibb.co/QYnzcbf/2.png)

3- create all classes and methods under new added library module.

4- after finishing library , upload project to github.

5- go to bintray website , and create account: 
[bintray](https://bintray.com/)

6- add Repository 
```bash
Note: use the same name, you already used in android Library. 
```
![alt text](https://i.ibb.co/xYxprHT/3.png)

7- under Type : select Maven. and Default License : Apache.

![alt text](https://i.ibb.co/8cK5sr3/4.png)

8- go to Repository and select add new Package.


![alt text](https://i.ibb.co/RcF7kJf/6.png)


9- go to package you already created and select create new version.


![alt text](https://i.ibb.co/0Z9mL46/7.png)

![alt text](https://i.ibb.co/YBq0wBP/8.png)

10 - now go to android project to setup bintray to your library.

11- go to Project Gradle and add  under dependencies:


![alt text](https://i.ibb.co/R0v0wZ3/9.png)


```bash
        classpath "com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.4"
        classpath "com.github.dcendents:android-maven-gradle-plugin:2.1"
```

12- under library build.gradle and fill all data with the same values you used in bintray : 


![alt text](https://i.ibb.co/fFBd4mt/10.png)


```bash
apply plugin: 'org.jetbrains.dokka-android'
  task dokkaJavadoc(type: org.jetbrains.dokka.gradle.DokkaTask) {
        outputFormat = 'javadoc'
        outputDirectory = "$buildDir/javadoc"
    }

    task javadocJars(type: Jar, dependsOn: dokkaJavadoc) {
        classifier = 'javadoc'
        from "$buildDir/javadoc"
    }
```


```bash
ext {
    bintrayRepo = 'lanlibrary'
    // Repo name in bintray dashboard

    bintrayName = 'com.example.lanlibrary'
    // package name of the bintray repo

    publishedGroupId = 'com.example.lanlibrary'
    // this is the ID we want to see in implementation line
    libraryName = 'lanlibrary'
    // this is the module name of library
    artifact = 'lanlibrary'
    // this is the artifact we want to see in implementation line

    libraryDescription = 'Test project library description'
    // description of library

    siteUrl = 'https://github.com/maliabd-al-majid/lanlibrary'
    // git repo url
    gitUrl =  'https://github.com/maliabd-al-majid/lanlibrary.git'
    // git repo vcs url

    libraryVersion = '1.0.1'
    // library version

    developerId = 'mohamednadeem'
    // This is your bintray username
    developerName = 'mohamednadeem'
    // Developer's name
    developerEmail = 'maliabd.al.majid@gmail.com'
    // Developer's email

    licenseName = 'The Apache Software License, Version 2.0'
    // for example, The Apache Software License, Version 2.0
    licenseUrl = 'http://www.apache.org/licenses/LICENSE-2.0.txt'
    // for example, http://www.apache.org/licenses/LICENSE-2.0.txt
    allLicenses = ["Apache-2.0"]
    // array of licenses, for example, ["Apache-2.0"]
}
apply from: 'publish.gradle'
```

13- copy and paste library buid.gradle , then rename it to be publish.gradle, remove all code and add following code:



![alt text](https://i.ibb.co/s53733p/11.png)


and for bintray key :


![alt text](https://i.ibb.co/3S88BLF/12.png)



```bash
apply plugin: 'com.jfrog.bintray'
apply plugin: 'com.github.dcendents.android-maven'

version = libraryVersion
group = publishedGroupId    // Maven Group ID for the artifact

install {
    repositories.mavenInstaller {
        // This generates POM.xml with proper parameters
        pom {
            project {
                packaging 'aar'
                groupId publishedGroupId
                artifactId artifact

                // Add your description here
                name libraryName
                description libraryDescription
                url siteUrl

                // Set your license
                licenses {
                    license {
                        name licenseName
                        url licenseUrl
                    }
                }
                developers {
                    developer {
                        id developerId
                        name developerName
                        email developerEmail
                    }
                }
                scm {
                    connection gitUrl
                    developerConnection gitUrl
                    url siteUrl

                }
            }
        }
    }
}


task sourcesJar(type: Jar) {
    from android.sourceSets.main.java.srcDirs
    classifier = 'sources'
}
task javadocJar(type: Jar, dependsOn: dokkaJavadoc) {
    classifier = 'javadoc'
    from "$buildDir/javadoc"
}

artifacts {
    archives javadocJar
    archives sourcesJar
}

// Bintray
Properties properties = new Properties()
properties.load(project.rootProject.file('local.properties').newDataInputStream())

bintray {
    user = 'username' // bintray username
    key = '12345678910abc'
 // bintray key you can find it under your profile.

    configurations = ['archives']
    pkg {
        repo = bintrayRepo
        name = bintrayName
        desc = libraryDescription
        websiteUrl = siteUrl
        vcsUrl = gitUrl
        licenses = allLicenses
        publish = true
        publicDownloadNumbers = true
        version {
            desc = libraryDescription
            // Uncomment 4 lines below to enable gpg auto signing
            //gpg {
            //    sign = true //Determines whether to GPG sign the files. The default is false
            //    passphrase = properties.getProperty("bintray.gpg.password")
            //}
        }
    }
}

```
14- go to Gradle Tasks:
 run install and run publishing Tasks.


![alt text](https://i.ibb.co/kG7LDRB/13.png)



15- go to bintray library and select package , then you can find gradle dependency :



![alt text](https://i.ibb.co/GcPnCZt/14.png)



