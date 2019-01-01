package com.example.demo;

import java.io.File;
import java.util.stream.Collectors;


import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UploadContoller {
	
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	Mono<Object> upload(@RequestBody Flux<Part> parts) {
		
		return parts.log().collectList().map(mparts -> {
			return mparts.stream().map(mmp -> {
				//System.out.println("------"+  (mmp instanceof FilePart));
				FilePart fp = (FilePart) mmp;
				fp.transferTo(new File("c:/hello/"+fp.filename()));
				//return mmp instanceof FilePart ? mmp.name() + ":" + ((FilePart) mmp).filename() : mmp.name();
				return fp.name() + ":" + fp.filename();
			}).collect(Collectors.joining(",", "[", "]"));
			
		});
        //return Mono.empty();
		
    };
    
    /*@RequestMapping(value = "upload", method = RequestMethod.POST)
	Mono<Object> upload(@RequestBody Flux<FilePart> parts) {
		
		return parts.log().collectList().map(mparts -> {
			return mparts.stream().map(mmp -> {
				mmp.transferTo(new File("c:/hello/"+mmp.filename()));
				return mmp.name() + ":" + mmp.filename();
			}).collect(Collectors.joining(",", "[", "]"));
			
		});
        //return Mono.empty();
		
    };*/
		
	

}
