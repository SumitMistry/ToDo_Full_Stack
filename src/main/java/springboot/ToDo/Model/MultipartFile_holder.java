package springboot.ToDo.Model;

import org.springframework.web.multipart.MultipartFile;

public class MultipartFile_holder {

        private MultipartFile multipartFile;

        public MultipartFile getMultipartFile() {
            return multipartFile;
        }

        public void setMultipartFile(MultipartFile multipartFile) {
            this.multipartFile = multipartFile;
        }

}
