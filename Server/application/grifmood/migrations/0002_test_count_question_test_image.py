# Generated by Django 4.1.2 on 2023-04-10 23:34

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('grifmood', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='test',
            name='count_question',
            field=models.IntegerField(default=10),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='test',
            name='image',
            field=models.CharField(default='/', max_length=255),
            preserve_default=False,
        ),
    ]