package example.day14;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173") // React 로컬 포트 허용
@RestController
@RequiredArgsConstructor 
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;
    // 등록
    @PostMapping("/write")
    public boolean write( @ModelAttribute BoardDto dto) {
        return boardService.boardWrite(dto);
    }

    // 전체 조회
    @GetMapping("/list")
    public List<BoardDto> list() {
        return boardService.boardFindAll();
    }

    // 개별 조회
    @GetMapping("/view")
    public BoardDto view(@RequestParam ( name = "id") Long id) {
        return boardService.boardFindById(id);
    }
    
    // 다운로드
    private final FileService fileService;
    @GetMapping ("/download/{id}")
    public void download(
        @PathVariable (name = "id") Long id,
        HttpServletResponse response){
            String fileName = boardService.getFileName(id);

            if (fileName != null) {
                fileService.fileDownload(fileName, response);
            }
        }
}