from django.shortcuts import HttpResponse
from django.shortcuts import render
import os
import tensorflow as tf
from tensorflow import keras
import matplotlib.pyplot as plt
import numpy as np 


from django.views.decorators.csrf import csrf_exempt

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

@csrf_exempt
def zzapsinsa(request):
    if request.method == 'GET':
        return HttpResponse("장고에서왔어요")
    elif request.method == 'POST':
        now = request.POST.get('name')
        print(now)
        print(request.POST)
        print("여기까지가 request에요 . ")
        return HttpResponse("장고에서왔어요")