package cafe.project.servlet.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final Path imageDirectory = Paths.get(
        System.getProperty("user.home"),
        "Downloads",
        "Cafe Project Images"
    ).toAbsolutePath().normalize();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String filename = request.getPathInfo();

        if (filename == null || filename.equals("/")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Prevent requests from escaping the image directory.
        Path imagePath = imageDirectory.resolve(filename.substring(1))
                                       .normalize();

        if (!imagePath.startsWith(imageDirectory)
                || !Files.isRegularFile(imagePath)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String contentType = Files.probeContentType(imagePath);

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        response.setContentType(contentType);
        response.setContentLengthLong(Files.size(imagePath));

        Files.copy(imagePath, response.getOutputStream());
    }
}