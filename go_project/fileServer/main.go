package main
import (
	"fmt"
	"net/http"
	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()
	
	
	r.GET("/", func(c *gin.Context) {
		c.String (http.StatusOK, "hello world!");
		name := c.DefaultQuery("name", "testname")
		c.String(http.StatusOK, fmt.Sprintf("name is %s", name))
	})
	
	r.GET("/download/:magnet/:filename", func(c *gin.Context) {
		magnet := c.Param("magnet")
		filename := c.Param("filename")
		fmt.Println(magnet)
		fmt.Println(filename)
	})
	/*
	r.Get("/", func(c *gin.Context) {
		
	})*/
	r.Run(":8000")
}
