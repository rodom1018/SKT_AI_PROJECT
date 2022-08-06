from django.shortcuts import HttpResponse
from django.shortcuts import render
import os
import tensorflow as tf
from tensorflow import keras
import matplotlib.pyplot as plt
import numpy as np 

from .forms import UploadFileForm

# Create your views here.
def index(request):
    form = UploadFileForm
    return render(request, 'index.html', {'form' : UploadFileForm})

def result(request):
    #new_model.summary()
    new_model = keras.models.load_model('./emotion_cnn_aug.hdf5')
    #my_image = request.FILES['file']
    #print(request.POST)
    if request.method == 'POST':
        form = UploadFileForm(request.POST, request.FILES)
        if form.is_valid():
            img = request.FILES['imgfile']
            print("if들어옴.")

        result = {
            #'input_image' : request.FILES['img']
        }
        return render(request, 'result.html', result)

def zzapsinsa(request):
     return HttpResponse("장고에서왔어요")