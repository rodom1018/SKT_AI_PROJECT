from django.db import models

# Create your models here.
class Image(models.Model):
    title = models.TextField(max_length=40 , null=True)
    imgfile = models.FileField( null=True, blank=True)
    #upload_to='default/',
    def __str__(self):
        return self.title